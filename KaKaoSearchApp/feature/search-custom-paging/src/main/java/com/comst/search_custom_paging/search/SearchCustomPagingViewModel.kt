package com.comst.search_custom_paging.search

import com.comst.search_custom_paging.search.SearchCustomPagingContract.*
import com.comst.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchCustomPagingViewModel @Inject constructor(

): BaseViewModel<SearchCustomPagingUIState, SearchCustomPagingSideEffect, SearchCustomPagingIntent, SearchCustomPagingEvent>(SearchCustomPagingUIState()) {

    override fun handleIntent(intent: SearchCustomPagingIntent) {
        when(intent){
            is SearchCustomPagingIntent.QueryChange -> onQueryChange(intent.query)
        }
    }

    override fun handleEvent(event: SearchCustomPagingEvent) {

    }

    private fun onQueryChange(query: String){
        setState {
            copy(
                queryValue = query
            )
        }
    }

}