package com.comst.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.comst.home.main.HomeMainRoute
import com.comst.ui.SnackbarToken

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.defaultRoute,navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    onShowSnackbar: (SnackbarToken) -> Unit,
    navigateBack: () -> Unit,
){
    composable(route = HomeRoute.defaultRoute){
        HomeMainRoute(
            padding = padding,
            onShowSnackBar = onShowSnackbar
        )
    }
}

object HomeRoute {
    const val defaultRoute = "home"
}