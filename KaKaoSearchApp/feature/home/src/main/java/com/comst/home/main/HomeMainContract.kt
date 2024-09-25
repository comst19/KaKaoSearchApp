package com.comst.home.main

import androidx.compose.runtime.Immutable
import com.comst.ui.base.BaseEvent
import com.comst.ui.base.BaseIntent
import com.comst.ui.base.BaseSideEffect
import com.comst.ui.base.BaseUIState

class HomeMainContract {

    @Immutable
    data class HomeMainUIState(
        val isLoading: Boolean = false
    ): BaseUIState

    sealed interface HomeMainSideEffect: BaseSideEffect {

    }

    sealed interface HomeMainIntent: BaseIntent {

    }

    sealed interface HomeMainEvent: BaseEvent {

    }
}