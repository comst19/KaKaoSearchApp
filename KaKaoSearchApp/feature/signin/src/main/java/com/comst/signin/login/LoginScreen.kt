package com.comst.signin.login

import android.content.res.Configuration
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
import com.comst.designsystem.theme.BaseTheme
import com.comst.signin.login.LoginContract.LoginIntent
import com.comst.signin.login.LoginContract.LoginUIState
import kotlinx.coroutines.delay

@Composable
internal fun LoginScreen(
    uiState: LoginUIState = LoginUIState(),
    setIntent: (LoginIntent) -> Unit = {}
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
            text = "로그인 화면",
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "홈으로",
            modifier = Modifier.clickable {
                setIntent(LoginIntent.HomeClick)
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "회원가입",
            modifier = Modifier.clickable {
                setIntent(LoginIntent.SignUpClick)
            }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GroupScreenPreview() {
    BaseTheme {
        LoginScreen()
    }
}