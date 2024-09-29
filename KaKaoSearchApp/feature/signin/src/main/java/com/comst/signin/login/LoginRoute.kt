package com.comst.signin.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.signin.login.LoginContract.LoginSideEffect
import com.comst.ui.SnackbarToken
import com.comst.ui.extension.collectAsStateWithLifecycle
import com.comst.ui.extension.collectWithLifecycle

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit

) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.effect.collectWithLifecycle { effect ->
        when(effect){
            is LoginSideEffect.NavigateToHome -> navigateToHome()
            is LoginSideEffect.NavigateToSignUp -> navigateToSignUp()
        }
    }

    LoginScreen(
        uiState = uiState,
        setIntent = viewModel::setIntent
    )
}