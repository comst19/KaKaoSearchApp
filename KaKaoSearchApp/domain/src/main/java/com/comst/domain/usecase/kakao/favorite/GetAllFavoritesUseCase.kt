package com.comst.domain.usecase.kakao.favorite

import com.comst.domain.repository.FavoriteRepository
import com.comst.domain.util.runCatchingIgnoreCancelled
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        favoriteRepository.getAllFavorites()
    }
}