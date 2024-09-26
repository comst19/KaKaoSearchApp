package com.comst.search_custom_paging.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.kakao.search.GetKaKaoMediaSearchSortedUseCase
import com.comst.search_custom_paging.search.SearchCustomPagingContract.*
import com.comst.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCustomPagingViewModel @Inject constructor(
    private val getKaKaoMediaSearchSortedUseCase: GetKaKaoMediaSearchSortedUseCase
): BaseViewModel<SearchCustomPagingUIState, SearchCustomPagingSideEffect, SearchCustomPagingIntent, SearchCustomPagingEvent>(SearchCustomPagingUIState()) {

    init {
        viewModelScope.launch {
            getKaKaoMediaSearchSortedUseCase().onSuccess { kaKoSearchFlow ->
                kaKoSearchFlow.collectLatest {
                    Log.d("흠","$it")
                }
            }.onFailure {

            }
        }

    }

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