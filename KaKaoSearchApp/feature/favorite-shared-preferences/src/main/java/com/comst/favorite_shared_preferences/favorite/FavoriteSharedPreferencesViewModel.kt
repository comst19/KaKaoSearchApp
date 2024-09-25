package com.comst.favorite_shared_preferences.favorite

import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.*
import com.comst.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteSharedPreferencesViewModel @Inject constructor(

): BaseViewModel<FavoriteSharedPreferencesUIState, FavoriteSharedPreferencesSideEffect, FavoriteSharedPreferencesIntent, FavoriteSharedPreferencesEvent>(FavoriteSharedPreferencesUIState()) {
    override fun handleIntent(intent: FavoriteSharedPreferencesIntent) {

    }

    override fun handleEvent(event: FavoriteSharedPreferencesEvent) {

    }

}