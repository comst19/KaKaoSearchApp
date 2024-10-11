package com.comst.domain.usecase.kakao.favorite

import com.comst.domain.repository.FavoriteRepository
import com.comst.domain.util.runCatchingIgnoreCancelled
import com.comst.model.KaKaoSearchContentModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        favoriteRepository.getAllFavorites().first().map {
            KaKaoSearchContentModel(
                isFavorite = true,
                media = it
            )
        }
    }
}