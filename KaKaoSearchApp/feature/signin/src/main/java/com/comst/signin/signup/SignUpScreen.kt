package com.comst.signin.signup

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.designsystem.theme.BaseTheme
import com.comst.signin.signup.SignUpContract.SignUpIntent
import com.comst.signin.signup.SignUpContract.SignUpSideEffect
import com.comst.signin.signup.SignUpContract.SignUpUIState
import com.comst.ui.SnackbarToken
import com.comst.ui.extension.collectAsStateWithLifecycle
import com.comst.ui.extension.collectWithLifecycle
import kotlinx.coroutines.delay

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

@Composable
private fun SignUpScreen(
    uiState: SignUpUIState = SignUpUIState(),
    setIntent: (SignUpIntent) -> Unit = {}
) {
    var animationStart by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        delay(300)
        animationStart = true
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "회원가입 화면",
        )

        Spacer(modifier = Modifier.height(40.dp))


        Text(
            text = "뒤로",
            modifier = Modifier.clickable {
                Log.d("회원가입", "뒤로")
                setIntent(SignUpIntent.BackClick)
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "홈으로",
            modifier = Modifier.clickable {
                Log.d("회원가입", "홈으로")
                setIntent(SignUpIntent.HomeClick)
            }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignUpScreenPreview() {
    BaseTheme {
        SignUpScreen()
    }
}