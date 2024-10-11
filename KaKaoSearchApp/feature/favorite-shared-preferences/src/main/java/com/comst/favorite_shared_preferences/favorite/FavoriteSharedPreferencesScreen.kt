package com.comst.favorite_shared_preferences.favorite

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.comst.designsystem.component.loading.LoadingWheel
import com.comst.designsystem.theme.BaseTheme
import com.comst.favorite_shared_preferences.R
import com.comst.favorite_shared_preferences.component.FavoriteMediaCard
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.FavoriteSharedPreferencesIntent
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.FavoriteSharedPreferencesUIState
import com.comst.ui_model.DisplayKaKaoSearchMedia

@Composable
internal fun FavoriteSharedPreferencesScreen(
    uiState: FavoriteSharedPreferencesUIState = FavoriteSharedPreferencesUIState(),
    padding: PaddingValues = PaddingValues(),
    setIntent: (FavoriteSharedPreferencesIntent) -> Unit = {}
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Text(
            text = stringResource(id = R.string.kakao_favorite_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp),
        )

        if(uiState.isLoading){
            LoadingWheel()
        }else{
            if (uiState.favoriteMediaList.isEmpty()) {
                KaKaoFavoriteEmpty()
            } else {
                KaKaoFavoriteGrid(
                    favoriteMediaList = uiState.favoriteMediaList
                )
            }
        }

    }
}

@Composable
private fun KaKaoFavoriteEmpty() {
    val emptyLottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_empty_box)
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LottieAnimation(
            modifier = Modifier
                .fillMaxSize(0.5f),
            composition = emptyLottieComposition,
            iterations = LottieConstants.IterateForever,
        )
        Text(
            text = stringResource(id = R.string.kakao_favorite_empty),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

private const val CELL_COUNT = 3
private val gridItemSpan: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(CELL_COUNT) }

@Composable
private fun KaKaoFavoriteGrid(
    modifier: Modifier = Modifier,
    favoriteMediaList: List<DisplayKaKaoSearchMedia>
){
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxHeight(),
        state = gridState,
        columns = GridCells.Fixed(CELL_COUNT),
        verticalArrangement = remember {
            object : Arrangement.Vertical {
                override fun Density.arrange(
                    totalSize: Int,
                    sizes: IntArray,
                    outPositions: IntArray
                ) {
                    var currentOffset = 0
                    sizes.forEachIndexed { index, size ->
                        if (index == sizes.lastIndex) {
                            outPositions[index] = totalSize - size
                        } else {
                            outPositions[index] = currentOffset
                            currentOffset += (size + 8.dp.toPx()).toInt()
                        }
                    }
                }
            }
        },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = remember { PaddingValues(8.dp) },
    ) {
        items(
            count = favoriteMediaList.size,
            key = { index -> index },
        ) { index ->
            val mediaItem = favoriteMediaList[index]

            FavoriteMediaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                media = mediaItem,
                onClickLink = { },
                onClickImage = { },
                onClickFavorite = { },
            )
        }

        item(
            span = gridItemSpan,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                KakaoSearchLastItem()
            }
        }
    }
}

@Composable
private fun KakaoSearchLastItem() {
    Text(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        text = stringResource(id = R.string.kakao_favorite_media_item_last),
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FavoriteSharedPreferencesScreenPreview() {
    BaseTheme {
        FavoriteSharedPreferencesScreen()
    }
}