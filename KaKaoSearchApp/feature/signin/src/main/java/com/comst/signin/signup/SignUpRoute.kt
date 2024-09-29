package com.comst.signin.signup

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.signin.signup.SignUpContract.SignUpSideEffect
import com.comst.ui.SnackbarToken
import com.comst.ui.extension.collectAsStateWithLifecycle
import com.comst.ui.extension.collectWithLifecycle

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (SnackbarToken) -> Unit
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.effect.collectWithLifecycle { effect ->
        when (effect) {
            is SignUpSideEffect.NavigateBack -> onBackClick()
            is SignUpSideEffect.NavigateToHome -> navigateToHome()
        }
    }


    SignUpScreen(
        uiState = uiState,
        setIntent = viewModel::setIntent
    )
}