package com.comst.favorite_shared_preferences.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.comst.designsystem.R
import com.comst.designsystem.theme.BaseTheme
import com.comst.designsystem.theme.Red300
import com.comst.model.KaKaoSearchMedia
import com.comst.model.KaKaoSearchMediaType
import com.comst.ui_model.DisplayKaKaoSearchMedia
import java.time.ZonedDateTime

@Composable
fun FavoriteMediaCard(
    modifier: Modifier = Modifier,
    media: DisplayKaKaoSearchMedia,
    onClickImage: (DisplayKaKaoSearchMedia) -> Unit,
    onClickLink: (DisplayKaKaoSearchMedia) -> Unit,
    onClickFavorite: (DisplayKaKaoSearchMedia) -> Unit,
) {
    Card(
        modifier = modifier
            .clickable(
                onClick = {
                    onClickImage(media)
                },
            ),
        shape = RectangleShape,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AsyncImage(
                model = media.kaKaoSearchMedia.thumbnailUrl,
                contentDescription = "Media Image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_open_in_new_24),
                    contentDescription = "OpenWeb",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(
                            shape = CircleShape
                        )
                        .clickable(
                            onClick = {
                                onClickLink(media)
                            },
                        )
                        .padding(6.dp)
                )
                Icon(
                    painter = painterResource(
                        id = if (media.isFavorite) {
                            R.drawable.ic_favorite_fill_24
                        } else {
                            R.drawable.ic_favorite_border_24
                        }
                    ),
                    tint = if (media.isFavorite) Red300 else Color.Unspecified,
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(
                            shape = CircleShape
                        )
                        .clickable(
                            onClick = {
                                onClickFavorite(media)
                            },
                        )
                        .padding(6.dp)
                )
            }

        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
internal fun MediaCardPreview() {
    BaseTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            FavoriteMediaCard(
                modifier = Modifier
                    .size(240.dp),
                media = DisplayKaKaoSearchMedia(
                    isFavorite = false,
                    KaKaoSearchMedia(
                        title = "Title",
                        url = "https://www.naver.com",
                        thumbnailUrl = "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
                        dateTime = ZonedDateTime.now().withNano(0),
                        mediaType = KaKaoSearchMediaType.IMAGE,
                    )
                ),
                onClickImage = { },
                onClickLink = { },
                onClickFavorite = { },
            )
        }
    }
}