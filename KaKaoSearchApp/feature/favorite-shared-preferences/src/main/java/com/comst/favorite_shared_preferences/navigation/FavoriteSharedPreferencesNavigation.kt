package com.comst.favorite_shared_preferences.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesRoute
import com.comst.ui.SnackbarToken

fun NavGraphBuilder.favoriteSharedPreferencesNavGraph(
    padding: PaddingValues,
    navigateFavoriteSharedPreferences: () -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    navigateBack: () -> Unit,
){
    composable(route = FavoriteSharedPreferencesRoute.defaultRoute){
        FavoriteSharedPreferencesRoute(
            padding = padding,
            onShowSnackBar = onShowSnackbar
        )
    }
}

object FavoriteSharedPreferencesRoute{
    const val defaultRoute = "favorite_shared_preferences"
}