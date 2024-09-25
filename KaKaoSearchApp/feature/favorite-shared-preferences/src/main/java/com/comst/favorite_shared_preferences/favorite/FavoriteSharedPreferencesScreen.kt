package com.comst.favorite_shared_preferences.favorite

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
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.*
import com.comst.favorite_shared_preferences.navigation.FavoriteSharedPreferencesRoute
import com.comst.ui.SnackbarToken
import com.comst.ui.extension.collectAsStateWithLifecycle
import com.comst.ui.extension.collectWithLifecycle

fun NavController.navigateFavoriteSharedPreferences(navOptions: NavOptions) {
    navigate(FavoriteSharedPreferencesRoute.defaultRoute,navOptions)
}

@Composable
fun FavoriteSharedPreferencesRoute(
    viewModel: FavoriteSharedPreferencesViewModel = hiltViewModel(),
    padding: PaddingValues,
    onShowSnackBar: (SnackbarToken) -> Unit
){

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.effect.collectWithLifecycle { effect ->

    }

    FavoriteSharedPreferencesScreen(
        uiState = uiState,
        padding = padding,
        setIntent = viewModel::setIntent
    )
}

@Composable
fun FavoriteSharedPreferencesScreen(
    uiState: FavoriteSharedPreferencesUIState = FavoriteSharedPreferencesUIState(),
    padding: PaddingValues = PaddingValues(),
    setIntent: (FavoriteSharedPreferencesIntent) -> Unit = {}
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "찜 목록",
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeMainScreenPreview() {
    BaseTheme {
        FavoriteSharedPreferencesScreen()
    }
}