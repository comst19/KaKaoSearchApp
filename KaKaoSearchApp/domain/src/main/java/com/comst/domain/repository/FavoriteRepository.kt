package com.comst.domain.repository

import com.comst.model.KaKaoSearchMedia
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun saveFavorite(mediaModel: KaKaoSearchMedia)
    fun deleteFavorite(mediaModel: KaKaoSearchMedia)
    fun getAllFavorites(): Flow<List<KaKaoSearchMedia>>
}