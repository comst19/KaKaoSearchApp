package com.comst.favorite_shared_preferences.favorite

import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.kakao.favorite.DeleteFavoriteUseCase
import com.comst.domain.usecase.kakao.favorite.GetAllFavoritesUseCase
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.*
import com.comst.ui.base.BaseViewModel
import com.comst.ui.util.OnetimeWhileSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoriteSharedPreferencesViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
): BaseViewModel<FavoriteSharedPreferencesUIState, FavoriteSharedPreferencesSideEffect, FavoriteSharedPreferencesIntent, FavoriteSharedPreferencesEvent>(FavoriteSharedPreferencesUIState()) {


    override fun handleIntent(intent: FavoriteSharedPreferencesIntent) {

    }

    override fun handleEvent(event: FavoriteSharedPreferencesEvent) {

    }

}