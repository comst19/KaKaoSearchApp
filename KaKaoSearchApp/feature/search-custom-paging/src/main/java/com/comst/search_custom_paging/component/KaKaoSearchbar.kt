package com.comst.search_custom_paging.component

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.comst.designsystem.theme.BaseTheme
import com.comst.search_custom_paging.R
import kotlinx.coroutines.delay

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun KaKaoSearchbar(
    modifier: Modifier = Modifier,
    active: Boolean = false,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onActiveChange: (Boolean) -> Unit = {},
    onQuery: () -> Unit = {},
    showBackButton: Boolean = false,
    onBackPress: () -> Unit = {},
) {
    val context = LocalContext.current

    val placeholderTextArray =
        remember { context.resources.getStringArray(R.array.kakao_search_placeholder) }
    val placeholderText by produceState(initialValue = placeholderTextArray.first()) {
        var index = 0
        while (true) {
            index++
            if (index >= placeholderTextArray.size) {
                index = 0
            }
            value = placeholderTextArray[index]
            delay(3000)
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val onSearch = {
        keyboardController?.hide()
        onQuery()
    }

    val onBackPressEvent = {
        keyboardController?.hide()
        onQueryChange("")
        onBackPress()
    }

    BackHandler(enabled = showBackButton) {
        onBackPressEvent()
    }

    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        query = query,
        onQueryChange = { textInput ->
            onQueryChange(textInput)
        },
        active = active,
        onActiveChange = onActiveChange,
        onSearch = { onSearch() },
        placeholder = {
            AnimatedContent(
                targetState = placeholderText,
                transitionSpec = {
                    val contentTransform =
                        slideInVertically { height -> height } + fadeIn() with
                                slideOutVertically { height -> -height } + fadeOut()
                    contentTransform.using(SizeTransform(clip = false))
                }, label = ""
            ) { targetString ->
                Text(
                    text = targetString,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        leadingIcon = {
            AnimatedVisibility(visible = showBackButton) {
                IconButton(
                    onClick = onBackPressEvent,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        },
        trailingIcon = {
            IconButton(
                onClick = { onSearch() },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search Icon"
                )
            }
        },
        content = {}
    )

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun KaKaoSearchbarPreview() {
    BaseTheme {
        KaKaoSearchbar()
    }
}

enum class KaKaoSearchUiState {
    IDLE,
    LOADING,
    EMPTY,
    SHOW_RESULT,
    ERROR,
}