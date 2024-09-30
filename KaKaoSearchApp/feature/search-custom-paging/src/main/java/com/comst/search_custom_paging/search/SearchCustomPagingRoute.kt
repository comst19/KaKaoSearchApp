package com.comst.search_custom_paging.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.comst.ui.SnackbarToken
import com.comst.ui.extension.collectAsStateWithLifecycle
import com.comst.ui.extension.collectWithLifecycle

@Composable
fun SearchCustomPagingRoute(
    viewModel: SearchCustomPagingViewModel = hiltViewModel(),
    padding: PaddingValues,
    onShowSnackBar: (SnackbarToken) -> Unit
){

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val listState = rememberLazyListState()

    viewModel.effect.collectWithLifecycle { effect ->

    }

    SearchCustomPagingScreen(
        uiState = uiState,
        padding = padding,
        listState = listState,
        setIntent = viewModel::setIntent
    )

}
