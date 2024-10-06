package com.comst.data.repository.kakao.favorite

import android.util.Log
import com.comst.data.repository.kakao.favorite.local.FavoriteLocalDataSource
import com.comst.domain.repository.FavoriteRepository
import com.comst.model.KaKaoSearchMedia
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoriteRepository {

    override fun getAllFavorites(): Flow<List<KaKaoSearchMedia>> {
        return favoriteLocalDataSource.getAllFavorites()
    }

    override fun saveFavorite(mediaModel: KaKaoSearchMedia) {
        try {
            favoriteLocalDataSource.saveFavorite(mediaModel)
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Failed to save favorite: ${e.message}")
            throw e
        }
    }

    override fun deleteFavorite(mediaModel: KaKaoSearchMedia) {
        try {
            favoriteLocalDataSource.deleteFavorite(mediaModel)
        } catch (e: Exception) {
            Log.e("FavoriteRepository", "Failed to delete favorite: ${e.message}")
            throw e
        }
    }
}