package com.comst.favorite_shared_preferences.favorite

import androidx.compose.runtime.Immutable
import com.comst.ui.base.BaseEvent
import com.comst.ui.base.BaseIntent
import com.comst.ui.base.BaseSideEffect
import com.comst.ui.base.BaseUIState
import com.comst.ui_model.DisplayKaKaoSearchMedia

class FavoriteSharedPreferencesContract {

    @Immutable
    data class FavoriteSharedPreferencesUIState(
        val isLoading: Boolean = true,
        val favoriteMediaList: List<DisplayKaKaoSearchMedia> = emptyList()
    ): BaseUIState

    sealed interface FavoriteSharedPreferencesSideEffect: BaseSideEffect {

    }

    sealed interface FavoriteSharedPreferencesIntent: BaseIntent {

    }

    sealed interface FavoriteSharedPreferencesEvent: BaseEvent {

    }
}