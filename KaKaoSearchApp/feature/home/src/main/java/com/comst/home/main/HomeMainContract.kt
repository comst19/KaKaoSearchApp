package com.comst.home.main

import androidx.compose.runtime.Immutable
import com.comst.ui.SnackbarToken
import com.comst.ui.base.BaseEvent
import com.comst.ui.base.BaseIntent
import com.comst.ui.base.BaseSideEffect
import com.comst.ui.base.BaseUIState

class HomeMainContract {

    @Immutable
    data class HomeMainUIState(
        val isLoading: Boolean = false,
        val snackbarToken: SnackbarToken = SnackbarToken(),
    ): BaseUIState

    sealed class HomeMainSideEffect: BaseSideEffect {

    }

    sealed class HomeMainIntent: BaseIntent {

    }

    sealed class HomeMainEvent: BaseEvent {

    }
}