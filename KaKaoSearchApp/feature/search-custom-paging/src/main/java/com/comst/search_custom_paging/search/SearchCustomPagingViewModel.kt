package com.comst.search_custom_paging.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.kakao.search.GetKaKaoMediaSearchSortedUseCase
import com.comst.domain.util.DomainResult
import com.comst.search_custom_paging.component.KaKaoSearchUiState
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingEvent
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingIntent
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingSideEffect
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingUIState
import com.comst.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCustomPagingViewModel @Inject constructor(
    private val getKaKaoMediaSearchSortedUseCase: GetKaKaoMediaSearchSortedUseCase,
) : BaseViewModel<SearchCustomPagingUIState, SearchCustomPagingSideEffect, SearchCustomPagingIntent, SearchCustomPagingEvent>(
    SearchCustomPagingUIState()
) {

    override fun handleIntent(intent: SearchCustomPagingIntent) {
        when (intent) {
            is SearchCustomPagingIntent.QueryChange -> onQueryChange(intent.query)
            is SearchCustomPagingIntent.MediaSearch -> {
                getKaKaoMediaSearchSortedUseCase.resetState()
                onMediaSearch()
            }
            is SearchCustomPagingIntent.NextPage -> onNextPage()
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
        setState {
            copy(
                kaKaoSearchState = KaKaoSearchUiState.LOADING
            )
        }
        getKaKaoMediaSearchSortedUseCase(
            query = currentState.queryValue
        ).collectLatest { domainResult ->
            when(domainResult){
                is DomainResult.Success -> {
                    val searchData = domainResult.data
                    setState {
                        copy(
                            kaKaoSearchList = kaKaoSearchList + searchData,
                            kaKaoSearchState = if (searchData.isNotEmpty()) KaKaoSearchUiState.SHOW_RESULT else KaKaoSearchUiState.EMPTY
                        )
                    }
                }
                is DomainResult.Failure -> {
                    setState {
                        copy(
                            kaKaoSearchState = KaKaoSearchUiState.ERROR
                        )
                    }
                }
            }
        }
    }

    private fun onNextPage(){
        onMediaSearch()
    }
}