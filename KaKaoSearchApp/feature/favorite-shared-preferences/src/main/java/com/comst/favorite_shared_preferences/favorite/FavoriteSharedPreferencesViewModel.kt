package com.comst.favorite_shared_preferences.favorite

import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.kakao.favorite.DeleteFavoriteUseCase
import com.comst.domain.usecase.kakao.favorite.GetAllFavoritesUseCase
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.FavoriteSharedPreferencesEvent
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.FavoriteSharedPreferencesIntent
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.FavoriteSharedPreferencesSideEffect
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.FavoriteSharedPreferencesUIState
import com.comst.ui.base.BaseViewModel
import com.comst.ui_model.toDisplayKaKaoSearchMedia
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteSharedPreferencesViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
): BaseViewModel<FavoriteSharedPreferencesUIState, FavoriteSharedPreferencesSideEffect, FavoriteSharedPreferencesIntent, FavoriteSharedPreferencesEvent>(FavoriteSharedPreferencesUIState()) {

    init {
        viewModelScope.launch {
            getAllFavoritesUseCase().onSuccess { favoriteList ->
                setState {
                    copy(
                        isLoading = false,
                        favoriteMediaList = favoriteList.map { it.toDisplayKaKaoSearchMedia() }
                    )
                }
            }.onFailure {
                setState {
                    copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun handleIntent(intent: FavoriteSharedPreferencesIntent) {

    }

    override fun handleEvent(event: FavoriteSharedPreferencesEvent) {

    }

}