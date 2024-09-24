package com.comst.search_custom_paging.search

import androidx.compose.runtime.Immutable
import com.comst.ui.SnackbarToken
import com.comst.ui.base.BaseEvent
import com.comst.ui.base.BaseIntent
import com.comst.ui.base.BaseSideEffect
import com.comst.ui.base.BaseUIState

class SearchCustomPagingContract {

    @Immutable
    data class SearchCustomPagingUIState(
        val isLoading: Boolean = false,
        val snackbarToken: SnackbarToken = SnackbarToken(),
    ): BaseUIState

    sealed class SearchCustomPagingSideEffect: BaseSideEffect {

    }

    sealed class SearchCustomPagingIntent: BaseIntent {

    }

    sealed class SearchCustomPagingEvent: BaseEvent {

    }
}