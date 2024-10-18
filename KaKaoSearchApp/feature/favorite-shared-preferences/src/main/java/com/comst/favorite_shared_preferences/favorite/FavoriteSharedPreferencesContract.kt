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
        val favoriteMediaList: List<DisplayKaKaoSearchMedia> = emptyList(),
        val canceledSet: Set<DisplayKaKaoSearchMedia> = emptySet()
    ): BaseUIState

    sealed interface FavoriteSharedPreferencesSideEffect: BaseSideEffect {
        data class NavigateToWeb(val uri: String): FavoriteSharedPreferencesSideEffect
    }

    sealed interface FavoriteSharedPreferencesIntent: BaseIntent {
        data class CancelFavorite(val displayKaKaoSearchMedia: DisplayKaKaoSearchMedia): FavoriteSharedPreferencesIntent
        data class ClickedImage(val uri: String): FavoriteSharedPreferencesIntent
        data class DownLoadImage(val originalUrl: String): FavoriteSharedPreferencesIntent
    }

    sealed interface FavoriteSharedPreferencesEvent: BaseEvent {
        data object LoadFavorites : FavoriteSharedPreferencesEvent
    }
}