package com.comst.data.repository.kakao.favorite.local

import android.content.SharedPreferences
import android.util.Log
import com.comst.data.network.MoshiProvider.toJsonString
import com.comst.data.network.MoshiProvider.toMediaModel
import com.comst.model.KaKaoSearchMedia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FavoriteLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : FavoriteLocalDataSource {

    private val favoritesKey = "favorites_key"

    private val _favoritesFlow = MutableStateFlow<List<KaKaoSearchMedia>>(loadFavoritesFromPrefs())
    val favoritesFlow: StateFlow<List<KaKaoSearchMedia>> = _favoritesFlow

    private fun loadFavoritesFromPrefs(): List<KaKaoSearchMedia> {
        val favoriteSet = sharedPreferences.getStringSet(favoritesKey, emptySet()) ?: emptySet()
        Log.d("쉐어드","${favoriteSet.size}")
        return favoriteSet.mapNotNull { it.toMediaModel() }
    }

    override fun saveFavorite(mediaModel: KaKaoSearchMedia) {
        val editor = sharedPreferences.edit()

        val existingFavorites = sharedPreferences.getStringSet(favoritesKey, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        existingFavorites.add(mediaModel.toJsonString())
        editor.putStringSet(favoritesKey, existingFavorites).apply()

        _favoritesFlow.value = loadFavoritesFromPrefs()
    }

    override fun deleteFavorite(mediaModel: KaKaoSearchMedia) {
        val editor = sharedPreferences.edit()

        val existingFavorites = sharedPreferences.getStringSet(favoritesKey, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        val jsonStringToDelete = mediaModel.toJsonString()

        if (existingFavorites.contains(jsonStringToDelete)) {
            existingFavorites.remove(jsonStringToDelete)
        }

        editor.putStringSet(favoritesKey, existingFavorites).apply()
        _favoritesFlow.value = loadFavoritesFromPrefs()
    }

    override fun getAllFavorites(): StateFlow<List<KaKaoSearchMedia>> {
        return favoritesFlow
    }
}