package com.comst.data.repository.kakao.favorite

import com.comst.data.repository.kakao.favorite.local.FavoriteLocalDataSource
import com.comst.domain.repository.FavoriteRepository
import com.comst.model.KaKaoSearchMediaModel
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoriteRepository {

    override fun saveFavorite(mediaModel: KaKaoSearchMediaModel) {
        favoriteLocalDataSource.saveFavorite(mediaModel)
    }

    override fun deleteFavorite(mediaModel: KaKaoSearchMediaModel) {
        favoriteLocalDataSource.deleteFavorite(mediaModel)
    }

    override fun getAllFavorites(): List<KaKaoSearchMediaModel> {
        return favoriteLocalDataSource.getAllFavorites()
    }
}