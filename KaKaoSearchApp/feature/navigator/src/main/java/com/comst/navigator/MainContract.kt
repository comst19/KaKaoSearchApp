package com.comst.navigator

import androidx.compose.runtime.Immutable
import com.comst.ui.SnackbarToken
import com.comst.ui.base.BaseEvent
import com.comst.ui.base.BaseIntent
import com.comst.ui.base.BaseSideEffect
import com.comst.ui.base.BaseUIState


class MainContract {

    @Immutable
    data class MainUIState(
        val snackbarToken: SnackbarToken = SnackbarToken(),
        val snackbarVisible: Boolean = false,
    ): BaseUIState

    sealed interface MainSideEffect : BaseSideEffect {

    }

    sealed interface MainIntent : BaseIntent {

    }

    sealed interface MainEvent : BaseEvent {
        data class ShowSnackbar(val snackbarToken: SnackbarToken) : MainEvent
    }
}