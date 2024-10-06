package com.comst.data.repository.kakao.favorite

import android.util.Log
import com.comst.data.repository.kakao.favorite.local.FavoriteLocalDataSource
import com.comst.domain.repository.FavoriteRepository
import com.comst.model.KaKaoSearchMediaModel
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoriteRepository {

    override fun saveFavorite(mediaModel: KaKaoSearchMediaModel) {
        try {
            favoriteLocalDataSource.saveFavorite(mediaModel)
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Failed to save favorite: ${e.message}")
            throw e
        }
    }

    override fun deleteFavorite(mediaModel: KaKaoSearchMediaModel) {
        try {
            favoriteLocalDataSource.deleteFavorite(mediaModel)
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Failed to delete favorite: ${e.message}")
            throw e
        }
    }

    override fun getAllFavorites(): List<KaKaoSearchMediaModel> {
        return try {
            favoriteLocalDataSource.getAllFavorites()
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Failed to get all favorites: ${e.message}")
            throw e
        }
    }
}