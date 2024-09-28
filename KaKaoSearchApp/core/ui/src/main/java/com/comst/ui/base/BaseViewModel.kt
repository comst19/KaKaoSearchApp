package com.comst.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comst.ui.util.ThrottleClickHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : BaseUIState, A : BaseSideEffect, I : BaseIntent, E : BaseEvent>(
    initialState: S,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _effect: Channel<A> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _intent: MutableSharedFlow<I> = MutableSharedFlow()
    private val intent = _intent.asSharedFlow()

    private val _event: MutableSharedFlow<E> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleException(exception)
    }

    private val throttleHandlers = mutableMapOf<String, ThrottleClickHandler>()
    protected fun canHandleClick(key: String, delayMillis: Long = 3000): Boolean {
        val handler = throttleHandlers.getOrPut(key) { ThrottleClickHandler(delayMillis) }
        return handler.canHandleClick()
    }

    init {
        viewModelScope.launch {
            intent.collect { intent ->
                handleIntent(intent)
            }
        }

        viewModelScope.launch {
            event.collect { event ->
                handleEvent(event)
            }
        }
    }

    protected val currentState: S
        get() = _uiState.value

    open fun setIntent(intent: I) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    open fun setEvent(event: E) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected abstract fun handleIntent(intent: I)

    protected abstract fun handleEvent(event: E)

    protected fun setState(reduce: S.() -> S) {
        val state = currentState.reduce()
        _uiState.value = state
    }

    protected fun setEffect(vararg builders: A) {
        for (effect in builders) {
            viewModelScope.launch { _effect.send(effect) }
        }
    }

    private fun handleException(exception: Throwable) {
        when (exception) {

        }
    }

    fun <T> Flow<T>.stateInWhileSubscribed(initialValue: T): StateFlow<T> {
        return stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = initialValue,
        )
    }
}