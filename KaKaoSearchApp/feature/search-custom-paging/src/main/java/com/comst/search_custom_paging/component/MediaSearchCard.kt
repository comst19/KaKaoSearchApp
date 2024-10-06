package com.comst.search_custom_paging.component

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.comst.designsystem.R
import com.comst.designsystem.theme.BaseTheme
import com.comst.designsystem.theme.Red300
import com.comst.model.KaKaoSearchMedia
import com.comst.model.KaKaoSearchMediaType
import com.comst.search_custom_paging.model.DisplayKaKaoSearchMedia
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MediaSearchCard(
    modifier: Modifier = Modifier,
    media: DisplayKaKaoSearchMedia,
    isExpanded: Boolean = false,
    onClickImage: (DisplayKaKaoSearchMedia) -> Unit,
    onClickLink: (DisplayKaKaoSearchMedia) -> Unit,
    onClickFavorite: (DisplayKaKaoSearchMedia) -> Unit,
) {
    val localDensity = LocalDensity.current

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault())
    val createdDateText = media.kaKaoSearchMedia.dateTime.format(formatter)

    val imageAspectRatio = 1f
    var imageUrl by remember { mutableStateOf(media.kaKaoSearchMedia.thumbnailUrl) }
    var isOriginalImageLoading by remember { mutableStateOf(false) }

    var imageWidthFractionTarget by rememberSaveable { mutableStateOf(0.3f) }
    var captionBackgroundAlphaTarget by rememberSaveable { mutableStateOf(0f) }
    var titlePaddingTarget by rememberSaveable { mutableStateOf(0.3f) }

    val imageWidthFraction: Float by animateFloatAsState(
        targetValue = imageWidthFractionTarget,
        animationSpec = tween(
            durationMillis = 300,
        ),
    )

    val captionBackgroundAlpha: Float by animateFloatAsState(
        targetValue = captionBackgroundAlphaTarget,
        animationSpec = tween(
            durationMillis = 300,
        ),
    )

    val titlePaddingFraction: Float by animateFloatAsState(
        targetValue = titlePaddingTarget,
        animationSpec = tween(
            durationMillis = 300,
        ),
    )

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            imageWidthFractionTarget = 1f
            captionBackgroundAlphaTarget = 0.5f
            titlePaddingTarget = 0f
            imageUrl = media.kaKaoSearchMedia.originalUrl ?: media.kaKaoSearchMedia.thumbnailUrl
            isOriginalImageLoading = true
        } else {
            imageWidthFractionTarget = 0.3f
            captionBackgroundAlphaTarget = 0f
            titlePaddingTarget = 0.3f
        }
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable(
                    onClick = {
                        onClickImage(media)
                    },
                ),
        ) {
            val imageSize = remember(imageWidthFractionTarget) {
                with(localDensity) {
                    Size(
                        (maxWidth * imageWidthFractionTarget).toPx().toInt(),
                        (maxWidth * imageAspectRatio * imageWidthFractionTarget).toPx().toInt(),
                    )
                }
            }

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .size(imageSize)
                    .crossfade(true)
                    .build(),
                contentDescription = "Media Image",
                modifier = Modifier
                    .fillMaxWidth(imageWidthFraction)
                    .aspectRatio(imageAspectRatio),
                contentScale = ContentScale.Crop,
                loading = {
                    if (isOriginalImageLoading) {
                        OriginalImageLoading()
                    }
                }
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(
                            alpha = captionBackgroundAlpha,
                        ),
                    )
                    .padding(start = maxWidth * titlePaddingFraction)
                    .padding(16.dp),
            ) {
                Text(
                    text = media.kaKaoSearchMedia.title,
                    modifier = Modifier
                        .wrapContentHeight(),
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = createdDateText,
                    modifier = Modifier
                        .wrapContentHeight(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(
                            alpha = captionBackgroundAlpha,
                        ),
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(6.dp),
                    painter = painterResource(
                        id = if (media.kaKaoSearchMedia.mediaType == KaKaoSearchMediaType.IMAGE) {
                            R.drawable.ic_image_24
                        } else {
                            R.drawable.ic_video_24
                        }
                    ),
                    contentDescription = "Media Type",
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_open_in_new_24),
                    contentDescription = "Open Web",
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

@Composable
private fun OriginalImageLoading() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(30.dp)
                .size(30.dp)
        )
        Text(
            text = stringResource(id = R.string.media_item_original_loading),
            style = MaterialTheme.typography.labelMedium
        )
    }
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
internal fun MediaSearchCardPreview() {
    BaseTheme {
        var expanded by rememberSaveable() { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxSize()) {
            MediaSearchCard(
                modifier = Modifier,
                isExpanded = expanded,
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
                onClickLink = {},
                onClickImage = {
                    expanded = !expanded
                },
                onClickFavorite = {},
            )
        }
    }
}