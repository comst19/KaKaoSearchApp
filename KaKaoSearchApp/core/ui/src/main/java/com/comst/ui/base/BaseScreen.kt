package com.comst.ui.base

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.comst.ui.extension.collectAsStateWithLifecycle
import com.comst.ui.extension.collectWithLifecycle

@Composable
fun <S : BaseUIState, A : BaseSideEffect, I : BaseIntent, E : BaseEvent> BaseScreen(
    viewModel: BaseViewModel<S, A, I, E>,
    handleEffect: (A) -> Unit = {},
    content: @Composable (S) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    viewModel.effect.collectWithLifecycle { effect ->
        when (effect) {
            is BaseSideEffect.ShowToast -> {

            }
            is BaseSideEffect.NavigateToLogin -> {

            }
            else -> handleEffect(effect)
        }
    }

    content(uiState)
}