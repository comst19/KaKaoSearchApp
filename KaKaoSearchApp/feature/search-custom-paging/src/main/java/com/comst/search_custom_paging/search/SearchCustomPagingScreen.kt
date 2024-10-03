package com.comst.search_custom_paging.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.comst.designsystem.component.error.ErrorScreen
import com.comst.search_custom_paging.component.MediaSearchCard
import com.comst.designsystem.component.loading.LoadingWheel
import com.comst.designsystem.theme.BaseTheme
import com.comst.search_custom_paging.model.DisplayKaKaoSearchMedia
import com.comst.search_custom_paging.R
import com.comst.search_custom_paging.component.KaKaoSearchUiState
import com.comst.search_custom_paging.component.KaKaoSearchbar
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingIntent
import com.comst.search_custom_paging.search.SearchCustomPagingContract.SearchCustomPagingUIState
import com.comst.ui.extension.OnBottomReached
import com.comst.ui.extension.PullToRefreshLazyColumn
import com.comst.ui.extension.isScrolledToEnd

@Composable
internal fun SearchCustomPagingScreen(
    uiState: SearchCustomPagingUIState = SearchCustomPagingUIState(),
    padding: PaddingValues = PaddingValues(),
    listState: LazyListState = rememberLazyListState(),
    setIntent: (SearchCustomPagingIntent) -> Unit = {}
){
    val mediaList: LazyPagingItems<DisplayKaKaoSearchMedia> = uiState.kaKaoSearchMedia.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
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
            onQuery = {
                setIntent(SearchCustomPagingIntent.MediaSearch)
            },
            showBackButton = uiState.showBackButton,
            onBackPress = {

            },
        )

        when (uiState.kaKaoSearchState){
            KaKaoSearchUiState.IDLE -> {
                KaKaoSearchIdleColumn()
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

            KaKaoSearchUiState.LOADING -> {
                LoadingWheel()
            }

            KaKaoSearchUiState.SHOW_RESULT -> {
                /*
                KaKaoSearchResultPagingColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    listState = listState,
                    mediaList = mediaList,
                    setIntent = setIntent
                )

                KaKaoSearchResultCustomPagingColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    listState = listState,
                    kaKaoSearchList = uiState.searchState.mediaList,
                    setIntent = setIntent
                )
                */

                KaKaoSearchResultCustomPagingColumnPull(
                    modifier = Modifier,
                    kaKaoSearchList = uiState.kaKaoSearchList,
                    isRefreshing = uiState.isRefreshing,
                    lazyListState = listState,
                    setIntent = setIntent
                )
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

@Composable
private fun KaKaoSearchResultPagingColumn(
    modifier: Modifier,
    mediaList: LazyPagingItems<DisplayKaKaoSearchMedia>,
    listState: LazyListState = rememberLazyListState(),
    setIntent: (SearchCustomPagingIntent) -> Unit
){
    val onReachedBottom by remember {
        derivedStateOf {
            listState.isScrolledToEnd()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            count = mediaList.itemCount,
            key = { index -> index }
        ){ index ->
            mediaList[index]?.run {
                var expanded by rememberSaveable { mutableStateOf(false) }
                MediaSearchCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    media = this,
                    isExpanded = expanded,
                    onClickLink = { },
                    onClickImage = {
                        expanded = !expanded
                    },
                    onClickFavorite = { },
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                if (onReachedBottom) {
                    KaKaoSearchLastItem()
                } else {
                    KaKaoSearchLoadingItem()
                }
            }
        }
    }

    listState.OnBottomReached(buffer = 20) {
        setIntent(SearchCustomPagingIntent.NextPage)
    }
}

@Composable
private fun KaKaoSearchResultCustomPagingColumn(
    modifier: Modifier,
    kaKaoSearchList: List<DisplayKaKaoSearchMedia> = emptyList(),
    listState: LazyListState = rememberLazyListState(),
    setIntent: (SearchCustomPagingIntent) -> Unit
){
    val sortedList = kaKaoSearchList.sortedByDescending { it.kaKaoSearchMedia.dateTime }
    val onReachedBottom by remember {
        derivedStateOf {
            listState.isScrolledToEnd()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            count = sortedList.size,
            key = { index -> index }
        ){ index ->
            sortedList[index].run {
                var expanded by rememberSaveable { mutableStateOf(false) }
                MediaSearchCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    media = this,
                    isExpanded = expanded,
                    onClickLink = { },
                    onClickImage = {
                        expanded = !expanded
                    },
                    onClickFavorite = { },
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                if (onReachedBottom) {
                    KaKaoSearchLastItem()
                } else {
                    KaKaoSearchLoadingItem()
                }
            }
        }
    }

    listState.OnBottomReached(buffer = 20) {
        setIntent(SearchCustomPagingIntent.NextPage)
    }
}

@Composable
private fun KaKaoSearchResultCustomPagingColumnPull(
    modifier: Modifier,
    kaKaoSearchList: List<DisplayKaKaoSearchMedia> = emptyList(),
    isRefreshing: Boolean,
    lazyListState: LazyListState = rememberLazyListState(),
    setIntent: (SearchCustomPagingIntent) -> Unit
) {
    val sortedList = kaKaoSearchList.sortedByDescending { it.kaKaoSearchMedia.dateTime }

    PullToRefreshLazyColumn(
        items = sortedList,
        content = { mediaItem ->
            mediaItem.run {
                var expanded by rememberSaveable { mutableStateOf(false) }
                MediaSearchCard(
                    modifier = modifier,
                    media = this,
                    isExpanded = expanded,
                    onClickLink = { },
                    onClickImage = {
                        expanded = !expanded
                    },
                    onClickFavorite = { },
                )
            }
        },
        onRefresh = { setIntent(SearchCustomPagingIntent.Refresh) },
        onLoadMore = { setIntent(SearchCustomPagingIntent.NextPage) },
        modifier = modifier,
        isRefreshing = isRefreshing,
        lazyListState = lazyListState
    )
}

@Composable
private fun ColumnScope.KaKaoSearchLoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(8.dp)
            .align(Alignment.CenterHorizontally)
    )
    Text(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        text = stringResource(id = R.string.kakao_search_media_item_loading),
    )
}

@Composable
private fun KaKaoSearchLastItem() {
    Text(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        text = stringResource(id = R.string.kakao_search_media_item_last),
    )
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeMainScreenPreview() {
    BaseTheme {
        SearchCustomPagingScreen()
    }
}