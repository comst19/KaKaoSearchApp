package com.comst.data.repository.kakao.favorite.local

import android.content.SharedPreferences
import com.comst.data.network.MoshiProvider.toJsonString
import com.comst.data.network.MoshiProvider.toMediaModel
import com.comst.model.KaKaoSearchMediaModel
import javax.inject.Inject

class FavoriteLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : FavoriteLocalDataSource {

    private val favoritesKey = "favorites_key"

    override fun saveFavorite(mediaModel: KaKaoSearchMediaModel) {
        val editor = sharedPreferences.edit()

        val existingFavorites = sharedPreferences.getStringSet(favoritesKey, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        existingFavorites.add(mediaModel.toJsonString())
        editor.putStringSet(favoritesKey, existingFavorites)
        editor.apply()
    }

    override fun deleteFavorite(mediaModel: KaKaoSearchMediaModel) {
        val editor = sharedPreferences.edit()

        val existingFavorites = sharedPreferences.getStringSet(favoritesKey, mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        val jsonStringToDelete = mediaModel.toJsonString()
        val updatedFavorites = existingFavorites.filterNot { it == jsonStringToDelete }.toMutableSet()

        editor.putStringSet(favoritesKey, updatedFavorites)
        editor.apply()
    }

    override fun getAllFavorites(): List<KaKaoSearchMediaModel> {
        val existingFavorites = sharedPreferences.getStringSet(favoritesKey, mutableSetOf()) ?: mutableSetOf()
        return existingFavorites.mapNotNull { jsonString ->
            jsonString.toMediaModel()
        }
    }
}