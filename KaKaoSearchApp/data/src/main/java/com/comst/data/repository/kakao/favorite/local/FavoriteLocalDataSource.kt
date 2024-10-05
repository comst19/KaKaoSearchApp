package com.comst.data.repository.kakao.favorite.local

import com.comst.model.KaKaoSearchMediaModel

interface FavoriteLocalDataSource {
    fun saveFavorite(mediaModel: KaKaoSearchMediaModel)
    fun deleteFavorite(mediaModel: KaKaoSearchMediaModel)
    fun getAllFavorites(): List<KaKaoSearchMediaModel>
}