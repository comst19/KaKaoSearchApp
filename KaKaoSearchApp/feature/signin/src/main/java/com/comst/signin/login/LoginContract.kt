package com.comst.signin.login

import androidx.compose.runtime.Immutable
import com.comst.ui.base.BaseEvent
import com.comst.ui.base.BaseIntent
import com.comst.ui.base.BaseSideEffect
import com.comst.ui.base.BaseUIState

class LoginContract {

    @Immutable
    data class LoginUIState(
        val isLoading : Boolean = false
    ): BaseUIState

    sealed interface LoginSideEffect : BaseSideEffect {
        data object NavigateToHome: LoginSideEffect
        data object NavigateToSignUp: LoginSideEffect
    }

    sealed interface LoginIntent : BaseIntent {
        data object SignUpClick: LoginIntent
        data object HomeClick: LoginIntent
    }

    sealed interface LoginEvent : BaseEvent {

    }
}