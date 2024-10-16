package com.comst.favorite_shared_preferences.favorite

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.comst.domain.usecase.download.ImageDownloadUseCase
import com.comst.domain.usecase.kakao.favorite.DeleteFavoriteUseCase
import com.comst.domain.usecase.kakao.favorite.GetAllFavoritesUseCase
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.FavoriteSharedPreferencesEvent
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.FavoriteSharedPreferencesIntent
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.FavoriteSharedPreferencesSideEffect
import com.comst.favorite_shared_preferences.favorite.FavoriteSharedPreferencesContract.FavoriteSharedPreferencesUIState
import com.comst.ui.base.BaseViewModel
import com.comst.ui_model.DisplayKaKaoSearchMedia
import com.comst.ui_model.toDisplayKaKaoSearchMedia
import com.comst.ui_model.toKaKaoSearchMedia
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteSharedPreferencesViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val imageDownloadUseCase: ImageDownloadUseCase
) : BaseViewModel<FavoriteSharedPreferencesUIState, FavoriteSharedPreferencesSideEffect, FavoriteSharedPreferencesIntent, FavoriteSharedPreferencesEvent>(
    FavoriteSharedPreferencesUIState()
) {

    override fun handleIntent(intent: FavoriteSharedPreferencesIntent) {
        when (intent) {
            is FavoriteSharedPreferencesIntent.CancelFavorite -> onCancelFavorite(intent.displayKaKaoSearchMedia)
            is FavoriteSharedPreferencesIntent.ClickedImage -> setEffect(FavoriteSharedPreferencesSideEffect.NavigateToWeb(intent.uri))
            is FavoriteSharedPreferencesIntent.DownLoadImage -> onImageDownload(intent.originalUrl)
        }
    }

    override fun handleEvent(event: FavoriteSharedPreferencesEvent) {
        when(event){
            FavoriteSharedPreferencesEvent.LoadFavorites -> onLoadFavorite()
        }
    }

    private fun onLoadFavorite() = viewModelScope.launch{
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

    private fun onCancelFavorite(displayKaKaoSearchMedia: DisplayKaKaoSearchMedia) = viewModelScope.launch {
        val kaKaoSearchMediaModel = displayKaKaoSearchMedia.toKaKaoSearchMedia()
        deleteFavoriteUseCase(kaKaoSearchMediaModel).onSuccess {
            setState {
                copy(
                    canceledSet = currentState.canceledSet + displayKaKaoSearchMedia,
                    favoriteMediaList = currentState.favoriteMediaList.filter {
                        it.kaKaoSearchMedia != displayKaKaoSearchMedia.kaKaoSearchMedia
                    }
                )
            }
        }.onFailure {

        }
    }

    private fun onImageDownload(originalUrl: String) = viewModelScope.launch {
        imageDownloadUseCase(originalUrl)
    }

}