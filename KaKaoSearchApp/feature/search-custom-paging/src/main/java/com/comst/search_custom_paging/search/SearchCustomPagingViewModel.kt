package com.comst.search_custom_paging.search

import com.comst.domain.usecase.kakao.search.GetKaKaoMediaSearchSortedUseCase
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingEvent
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingIntent
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingSideEffect
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingUIState
import com.comst.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

}