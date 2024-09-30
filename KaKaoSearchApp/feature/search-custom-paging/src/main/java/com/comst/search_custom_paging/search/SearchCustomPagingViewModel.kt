package com.comst.search_custom_paging.search

import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.comst.search_custom_paging.model.toDisplayKaKaoSearchMedia
import com.comst.domain.usecase.kakao.search.GetKaKaoMediaSearchPagingUseCase
import com.comst.search_custom_paging.component.KaKaoSearchUiState
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingEvent
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingIntent
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingSideEffect
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingUIState
import com.comst.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCustomPagingViewModel @Inject constructor(
    private val getKaKaoMediaSearchPagingUseCase: GetKaKaoMediaSearchPagingUseCase
) : BaseViewModel<SearchCustomPagingUIState, SearchCustomPagingSideEffect, SearchCustomPagingIntent, SearchCustomPagingEvent>(
    SearchCustomPagingUIState()
) {

    override fun handleIntent(intent: SearchCustomPagingIntent) {
        when (intent) {
            is SearchCustomPagingIntent.QueryChange -> onQueryChange(intent.query)
            is SearchCustomPagingIntent.MediaSearch -> {
                setState {
                    copy(
                        kaKaoSearchState = KaKaoSearchUiState.LOADING
                    )
                }
                onMediaSearch()
            }
            is SearchCustomPagingIntent.NextPage -> onMediaSearch()
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
                pagingData.map{ media ->
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
}