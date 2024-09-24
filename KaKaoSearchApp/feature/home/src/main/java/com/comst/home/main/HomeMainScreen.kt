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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.comst.designsystem.theme.BaseTheme
import com.comst.home.main.HomeMainContract.HomeMainIntent
import com.comst.home.main.HomeMainContract.HomeMainSideEffect
import com.comst.home.main.HomeMainContract.HomeMainUIState
import com.comst.home.navigation.HomeRoute
import com.comst.ui.SnackbarToken
import com.comst.ui.base.BaseScreen

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.defaultRoute,navOptions)
}

@Composable
fun HomeMainRoute(
    viewModel: HomeMainViewModel = hiltViewModel(),
    padding: PaddingValues,
    onShowSnackBar: (SnackbarToken) -> Unit
) {

    val handleEffect: (HomeMainSideEffect) -> Unit = { effect ->

    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        HomeMainScreen(
            uiState = uiState,
            padding = padding,
            setIntent = viewModel::setIntent
        )
    }
}


@Composable
private fun HomeMainScreen(
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