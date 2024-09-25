package com.comst.signin.signup

import com.comst.signin.signup.SignUpContract.*
import com.comst.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

): BaseViewModel<SignUpUIState, SignUpSideEffect, SignUpIntent, SignUpEvent>(SignUpUIState()){

    override fun handleIntent(intent: SignUpIntent) {
        when(intent){
            SignUpIntent.BackClick -> setEffect(SignUpSideEffect.NavigateBack)
            SignUpIntent.HomeClick -> setEffect(SignUpSideEffect.NavigateToHome)
        }
    }

    override fun handleEvent(event: SignUpEvent) {

    }

}