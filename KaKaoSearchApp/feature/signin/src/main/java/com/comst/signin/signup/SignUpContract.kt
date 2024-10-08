package com.comst.signin.signup

import androidx.compose.runtime.Immutable
import com.comst.ui.base.BaseEvent
import com.comst.ui.base.BaseIntent
import com.comst.ui.base.BaseSideEffect
import com.comst.ui.base.BaseUIState

class SignUpContract {

    @Immutable
    data class SignUpUIState(
        val isLoading: Boolean = false,
    ): BaseUIState

    sealed interface SignUpSideEffect : BaseSideEffect {
        data object NavigateToHome: SignUpSideEffect
        data object NavigateBack: SignUpSideEffect
    }

    sealed interface SignUpIntent : BaseIntent {
        data object HomeClick: SignUpIntent
        data object BackClick: SignUpIntent
    }

    sealed interface SignUpEvent : BaseEvent {

    }
}