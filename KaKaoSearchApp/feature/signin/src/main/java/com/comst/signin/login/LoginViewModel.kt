package com.comst.signin.login

import com.comst.signin.login.LoginContract.*
import com.comst.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

): BaseViewModel<LoginUIState, LoginSideEffect, LoginIntent, LoginEvent>(LoginUIState()){

    override fun handleIntent(intent: LoginIntent) {
        when(intent){
            LoginIntent.HomeClick -> setEffect(LoginSideEffect.NavigateToHome)
            LoginIntent.SignUpClick -> setEffect(LoginSideEffect.NavigateToSignUp)
        }
    }

}