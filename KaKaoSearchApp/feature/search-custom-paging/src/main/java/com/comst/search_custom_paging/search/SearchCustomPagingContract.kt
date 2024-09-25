package com.comst.search_custom_paging.search

import androidx.compose.runtime.Immutable
import com.comst.ui.base.BaseEvent
import com.comst.ui.base.BaseIntent
import com.comst.ui.base.BaseSideEffect
import com.comst.ui.base.BaseUIState

class SearchCustomPagingContract {

    @Immutable
    data class SearchCustomPagingUIState(
        val isLoading: Boolean = false,
    ): BaseUIState

    sealed interface SearchCustomPagingSideEffect: BaseSideEffect {

    }

    sealed interface SearchCustomPagingIntent: BaseIntent {

    }

    sealed interface SearchCustomPagingEvent: BaseEvent {

    }
}