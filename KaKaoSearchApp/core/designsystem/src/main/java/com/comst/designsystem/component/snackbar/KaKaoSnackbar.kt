package com.comst.designsystem.component.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comst.designsystem.R
import com.comst.designsystem.theme.Background30
import com.comst.designsystem.theme.BaseTheme
import com.comst.designsystem.theme.BaseTypography
import com.comst.designsystem.theme.Primary200

@Composable
fun KaKaoSnackbar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    message: String,
    @DrawableRes actionIconId: Int? = null,
    actionButtonText: String? = null,
    onClickActionButton: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            KaKaoSnackbarContent(
                modifier = Modifier,
                message = message,
                actionIconId = actionIconId,
                actionButtonText = actionButtonText,
                onClickActionButton = onClickActionButton,
            )
        }
    }
}

@Composable
private fun KaKaoSnackbarContent(
    modifier: Modifier = Modifier,
    message: String,
    @DrawableRes actionIconId: Int? = null,
    actionButtonText: String? = null,
    onClickActionButton: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(color = Background30, shape = RoundedCornerShape(4.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = modifier.weight(1f), text = message,
            style = BaseTypography.bodyMedium
        )

        if (actionIconId != null) {
            Icon(
                painter = painterResource(id = actionIconId),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClickActionButton() },
                tint = Primary200
            )
        }

        if (actionButtonText != null) {
            Text(
                modifier = Modifier
                    .clickable(
                        onClick = onClickActionButton,
                    ),
                text = actionButtonText,
                style = BaseTypography.bodySmall.copy(color = Primary200)
            )
        }
    }
}


@Preview
@Composable
fun BaseSnackbarPreview() {
    BaseTheme {
        Column {
            KaKaoSnackbarContent(message = "This is a snackbar")
            KaKaoSnackbarContent(message = "This is a snackbar This is a snackbar This is a snackbar ")

            KaKaoSnackbarContent(message = "This is a snackbar", actionButtonText = "Action")

            KaKaoSnackbarContent(
                message = "This is a snackbar",
                actionIconId = R.drawable.ic_arrow_back_24
            )

            KaKaoSnackbarContent(
                message = "This is a snackbar This is a snackbar This is a snackbar ",
                actionButtonText = "Action"
            )

            KaKaoSnackbarContent(
                message = "This is a snackbar This is a snackbar This is a snackbar ",
                actionIconId = R.drawable.ic_arrow_back_24
            )
        }
    }
}