package com.comst.domain.repository

import com.comst.model.KaKaoSearchMediaModel

interface FavoriteRepository {
    fun saveFavorite(mediaModel: KaKaoSearchMediaModel)
    fun deleteFavorite(mediaModel: KaKaoSearchMediaModel)
    fun getAllFavorites(): List<KaKaoSearchMediaModel>
}