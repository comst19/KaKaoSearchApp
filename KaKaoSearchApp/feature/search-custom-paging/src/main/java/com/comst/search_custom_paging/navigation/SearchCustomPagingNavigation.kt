package com.comst.search_custom_paging.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.comst.search_custom_paging.search.SearchCustomPagingRoute
import com.comst.ui.SnackbarToken

fun NavGraphBuilder.searchCustomPagingRouteNavGraph(
    padding: PaddingValues,
    navigateSearchCustomPaging: () -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    navigateBack: () -> Unit,
){
    composable(route = SearchCustomPagingRoute.defaultRoute){
        SearchCustomPagingRoute(
            padding = padding,
            onShowSnackBar = onShowSnackbar
        )
    }
}

object SearchCustomPagingRoute{
    const val defaultRoute = "search_custom_paging"
}