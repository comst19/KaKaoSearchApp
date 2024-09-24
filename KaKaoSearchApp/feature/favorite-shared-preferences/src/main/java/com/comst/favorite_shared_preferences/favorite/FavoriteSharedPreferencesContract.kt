package com.comst.favorite_shared_preferences.favorite

import androidx.compose.runtime.Immutable
import com.comst.ui.SnackbarToken
import com.comst.ui.base.BaseEvent
import com.comst.ui.base.BaseIntent
import com.comst.ui.base.BaseSideEffect
import com.comst.ui.base.BaseUIState

class FavoriteSharedPreferencesContract {

    @Immutable
    data class FavoriteSharedPreferencesUIState(
        val isLoading: Boolean = false,
        val snackbarToken: SnackbarToken = SnackbarToken(),
    ): BaseUIState

    sealed class FavoriteSharedPreferencesSideEffect: BaseSideEffect {

    }

    sealed class FavoriteSharedPreferencesIntent: BaseIntent {

    }

    sealed class FavoriteSharedPreferencesEvent: BaseEvent {

    }
}