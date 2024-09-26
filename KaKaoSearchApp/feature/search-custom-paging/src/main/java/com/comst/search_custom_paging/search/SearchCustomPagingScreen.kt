package com.comst.search_custom_paging.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.comst.designsystem.component.error.ErrorScreen
import com.comst.designsystem.component.loading.LoadingWheel
import com.comst.search_custom_paging.component.KaKaoSearchUiState
import com.comst.search_custom_paging.component.KaKaoSearchbar
import com.comst.designsystem.theme.BaseTheme
import com.comst.search_custom_paging.R
import com.comst.search_custom_paging.navigation.SearchCustomPagingRoute
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingIntent
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingUIState
import com.comst.ui.SnackbarToken
import com.comst.ui.extension.collectAsStateWithLifecycle
import com.comst.ui.extension.collectWithLifecycle

fun NavController.navigateSearchCustomPaging(navOptions: NavOptions) {
    navigate(SearchCustomPagingRoute.defaultRoute,navOptions)
}

@Composable
fun SearchCustomPagingRoute(
    viewModel: SearchCustomPagingViewModel = hiltViewModel(),
    padding: PaddingValues,
    onShowSnackBar: (SnackbarToken) -> Unit
){

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.effect.collectWithLifecycle { effect ->

    }

    SearchCustomPagingScreen(
        uiState = uiState,
        padding = padding,
        setIntent = viewModel::setIntent
    )

}

@Composable
fun SearchCustomPagingScreen(
    uiState: SearchCustomPagingUIState = SearchCustomPagingUIState(),
    padding: PaddingValues = PaddingValues(),
    setIntent: (SearchCustomPagingIntent) -> Unit = {}
){
    Column(
        modifier = Modifier.fillMaxSize().padding(padding)
    ) {
        KaKaoSearchbar(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            active = false,
            query = uiState.queryValue,
            onQueryChange = { query ->
                setIntent(SearchCustomPagingIntent.QueryChange(query))
            },
            onQuery = { },
            showBackButton = uiState.showBackButton,
            onBackPress = {

            },
        )

        when (uiState.kaKaoSearchState){
            KaKaoSearchUiState.IDLE -> {
                KaKaoSearchIdleColumn()
            }

            KaKaoSearchUiState.LOADING -> {
                LoadingWheel()
            }

            KaKaoSearchUiState.EMPTY -> {
                KaKaoSearchEmpty()
            }

            KaKaoSearchUiState.ERROR -> {
                ErrorScreen(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    onRetry = { },
                )
            }

            KaKaoSearchUiState.SHOW_RESULT -> {

            }
        }
    }
}

@Composable
private fun KaKaoSearchIdleColumn() {
    Text(
        modifier = Modifier
            .padding(36.dp),
        text = stringResource(id = R.string.kakao_search_headline),
        style = MaterialTheme.typography.headlineLarge,
        softWrap = true,
        lineHeight = 48.sp,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun KaKaoSearchEmpty() {
    val emptyLottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_empty)
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LottieAnimation(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .wrapContentHeight(),
            composition = emptyLottieComposition,
            iterations = LottieConstants.IterateForever,
        )
        Text(
            text = stringResource(id = R.string.kakao_search_media_item_empty),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
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