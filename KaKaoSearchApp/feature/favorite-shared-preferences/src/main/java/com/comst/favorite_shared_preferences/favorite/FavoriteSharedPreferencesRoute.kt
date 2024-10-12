package com.comst.favorite_shared_preferences.favorite

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
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

    LaunchedEffect(true) {
        viewModel.setEvent(
            FavoriteSharedPreferencesEvent.LoadFavorites
        )
    }

    FavoriteSharedPreferencesScreen(
        uiState = uiState,
        padding = padding,
        setIntent = viewModel::setIntent
    )
}