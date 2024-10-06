package com.comst.data.repository.kakao.favorite.local

import com.comst.model.KaKaoSearchMedia
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalDataSource {
    fun saveFavorite(mediaModel: KaKaoSearchMedia)
    fun deleteFavorite(mediaModel: KaKaoSearchMedia)
    fun getAllFavorites(): Flow<List<KaKaoSearchMedia>>
}