package com.comst.ui.extension

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PullToRefreshLazyColumn(
    items: List<T>,
    content: @Composable (T) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val onReachedBottom by remember {
        derivedStateOf {
            lazyListState.isScrolledToEnd()
        }
    }

    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) {
                content(it)
            }

            if (onReachedBottom) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        if (pullToRefreshState.isRefreshing || pullToRefreshState.progress > 0) {
            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                onRefresh()
            }
        }


        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }

        lazyListState.OnBottomReached(buffer = 20) {
            onLoadMore()
        }
    }
}