package com.comst.search_custom_paging.search

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.comst.search_custom_paging.model.DisplayKaKaoSearchMedia
import com.comst.search_custom_paging.component.KaKaoSearchUiState
import com.comst.ui.base.BaseEvent
import com.comst.ui.base.BaseIntent
import com.comst.ui.base.BaseSideEffect
import com.comst.ui.base.BaseUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class SearchCustomPagingContract {

    @Immutable
    data class SearchCustomPagingUIState(
        val isLoading: Boolean = false,
        val queryValue: String = "",
        val showBackButton: Boolean = false,
        val kaKaoSearchMedia: Flow<PagingData<DisplayKaKaoSearchMedia>> = emptyFlow(),
        val kaKaoSearchList: List<DisplayKaKaoSearchMedia> = emptyList(),
        val kaKaoSearchState: KaKaoSearchUiState = KaKaoSearchUiState.IDLE
    ): BaseUIState

    sealed interface SearchCustomPagingSideEffect: BaseSideEffect {

    }

    sealed interface SearchCustomPagingIntent: BaseIntent {
        data class QueryChange(val query: String): SearchCustomPagingIntent
        data object MediaSearch: SearchCustomPagingIntent
        data object NextPage: SearchCustomPagingIntent
    }

    sealed interface SearchCustomPagingEvent: BaseEvent {

    }
}