package com.comst.search_custom_paging.search

import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.comst.domain.usecase.kakao.favorite.DeleteFavoriteUseCase
import com.comst.domain.usecase.kakao.favorite.SaveFavoriteUseCase
import com.comst.domain.usecase.kakao.search.GetKaKaoMediaSearchPagingUseCase
import com.comst.domain.usecase.kakao.search.GetKaKaoMediaSearchSortedUseCase
import com.comst.domain.util.onFailure
import com.comst.domain.util.onSuccess
import com.comst.search_custom_paging.component.KaKaoSearchUiState
import com.comst.search_custom_paging.model.DisplayKaKaoSearchMedia
import com.comst.search_custom_paging.model.toDisplayKaKaoSearchMedia
import com.comst.search_custom_paging.model.toKaKaoSearchMedia
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingEvent
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingIntent
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingSideEffect
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingUIState
import com.comst.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCustomPagingViewModel @Inject constructor(
    private val getKaKaoMediaSearchPagingUseCase: GetKaKaoMediaSearchPagingUseCase,
    private val getKaKaoMediaSearchSortedUseCase: GetKaKaoMediaSearchSortedUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : BaseViewModel<SearchCustomPagingUIState, SearchCustomPagingSideEffect, SearchCustomPagingIntent, SearchCustomPagingEvent>(
    SearchCustomPagingUIState()
) {

    override fun handleIntent(intent: SearchCustomPagingIntent) {
        when (intent) {
            is SearchCustomPagingIntent.QueryChange -> onQueryChange(intent.query)
            is SearchCustomPagingIntent.MediaSearch -> onMediaSearchResult(true)
            is SearchCustomPagingIntent.Refresh -> onMediaSearchResult(true)
            is SearchCustomPagingIntent.NextPage -> onMediaSearchResult(false)
            is SearchCustomPagingIntent.ToggleFavorite -> onToggleFavorite(intent.displayKaKaoSearchMedia)
        }
    }

    override fun handleEvent(event: SearchCustomPagingEvent) {

    }

    private fun onQueryChange(query: String) {
        setState {
            copy(
                queryValue = query
            )
        }
    }

    private fun onMediaSearch() = viewModelScope.launch {
        getKaKaoMediaSearchPagingUseCase(
            query = currentState.queryValue
        ).onSuccess { searchMediaFlow ->
            val displayKaKaoSearchMediaFlow = searchMediaFlow.map { pagingData ->
                pagingData.map { media ->
                    media.toDisplayKaKaoSearchMedia()
                }
            }

            setState {
                copy(
                    kaKaoSearchState = KaKaoSearchUiState.SHOW_RESULT,
                    kaKaoSearchMedia = displayKaKaoSearchMediaFlow
                )
            }
        }.onFailure {
            setState {
                copy(
                    kaKaoSearchState = KaKaoSearchUiState.ERROR
                )
            }
        }
    }

    private fun onMediaSearchResult(refresh: Boolean) = viewModelScope.launch {
        if (refresh){
            setState {
                copy(
                    kaKaoSearchList = emptyList(),
                    kaKaoSearchState = KaKaoSearchUiState.LOADING,
                    isRefreshing = true
                )
            }
        }
        getKaKaoMediaSearchSortedUseCase(
            query = currentState.queryValue,
            refresh = refresh
        ).collectLatest { result ->
            result.onSuccess { searchDomainModel ->
                setState {
                    copy(
                        kaKaoSearchList = currentState.kaKaoSearchList + searchDomainModel.itemList.map { it.toDisplayKaKaoSearchMedia() },
                        kaKaoSearchState = KaKaoSearchUiState.SHOW_RESULT,
                        isRefreshing = false
                    )
                }
                searchDomainModel.itemList.map { it.toDisplayKaKaoSearchMedia() }
            }.onFailure {
                setState {
                    copy(
                        kaKaoSearchState = KaKaoSearchUiState.ERROR,
                        isRefreshing = false
                    )
                }
            }
        }
    }

    private fun onToggleFavorite(displayKaKaoSearchMedia: DisplayKaKaoSearchMedia) = viewModelScope.launch {

        val kaKaoSearchMediaModel = displayKaKaoSearchMedia.toKaKaoSearchMedia()

        if (displayKaKaoSearchMedia.isFavorite) {
            deleteFavoriteUseCase(kaKaoSearchMediaModel).onSuccess {
                setState {
                    copy(
                        kaKaoSearchList = kaKaoSearchList.map {
                            if (it == displayKaKaoSearchMedia) {
                                it.copy(isFavorite = false)
                            } else {
                                it
                            }
                        }
                    )
                }
            }.onFailure {

            }
        } else {
            saveFavoriteUseCase(kaKaoSearchMediaModel).onSuccess {
                setState {
                    copy(
                        kaKaoSearchList = kaKaoSearchList.map {
                            if (it == displayKaKaoSearchMedia) {
                                it.copy(isFavorite = true)
                            } else {
                                it
                            }
                        }
                    )
                }
            }.onFailure {

            }
        }
    }
}