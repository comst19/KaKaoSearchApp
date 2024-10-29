package com.comst.navigator

import androidx.lifecycle.viewModelScope
import com.comst.navigator.MainContract.*
import com.comst.ui.SnackbarToken
import com.comst.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): BaseViewModel<MainUIState, MainSideEffect, MainIntent, MainEvent>(MainUIState()){

    private val mutex = Mutex()

    override fun handleIntent(intent: MainIntent) {

    }

    override fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ShowSnackbar -> onShowSnackbar(event.snackbarToken)
        }
    }


    private fun onShowSnackbar(snackbarToken: SnackbarToken){
        viewModelScope.launch {
            mutex.withLock {
                setState {
                    copy(
                        snackbarToken = snackbarToken,
                        snackbarVisible = true
                    )
                }
                delay(SHOW_TOAST_LENGTH)
                setState { copy(snackbarVisible = false) }
            }
        }
    }

    companion object {
        private const val SHOW_TOAST_LENGTH = 2000L
    }

}