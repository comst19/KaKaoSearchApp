package com.comst.home.main

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.comst.designsystem.theme.BaseTheme
import com.comst.home.main.HomeMainContract.HomeMainIntent
import com.comst.home.main.HomeMainContract.HomeMainUIState

@Composable
internal fun HomeMainScreen(
    uiState: HomeMainUIState = HomeMainUIState(),
    padding: PaddingValues = PaddingValues(),
    setIntent: (HomeMainIntent) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "메인 홈 화면",
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeMainScreenPreview() {
    BaseTheme {
        HomeMainScreen(

        )
    }
}