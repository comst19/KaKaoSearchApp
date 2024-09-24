package com.comst.search_custom_paging.search

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
import com.comst.search_custom_paging.navigation.SearchCustomPagingRoute
import com.comst.search_custom_paging.search.SearchCustomPagingContract.*
import com.comst.ui.SnackbarToken
import com.comst.ui.base.BaseScreen

fun NavController.navigateSearchCustomPaging(navOptions: NavOptions) {
    navigate(SearchCustomPagingRoute.defaultRoute,navOptions)
}

@Composable
fun SearchCustomPagingRoute(
    viewModel: SearchCustomPagingViewModel = hiltViewModel(),
    padding: PaddingValues,
    onShowSnackBar: (SnackbarToken) -> Unit
){
    val handleEffect: (SearchCustomPagingSideEffect) -> Unit = { effect ->

    }

    BaseScreen(viewModel = viewModel, handleEffect = handleEffect) { uiState ->
        SearchCustomPagingScreen(
            uiState = uiState,
            padding = padding,
            setIntent = viewModel::setIntent
        )
    }
}

@Composable
fun SearchCustomPagingScreen(
    uiState: SearchCustomPagingUIState = SearchCustomPagingUIState(),
    padding: PaddingValues = PaddingValues(),
    setIntent: (SearchCustomPagingIntent) -> Unit = {}
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "검색 목록 - 페이징",
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeMainScreenPreview() {
    BaseTheme {
        SearchCustomPagingScreen()
    }
}