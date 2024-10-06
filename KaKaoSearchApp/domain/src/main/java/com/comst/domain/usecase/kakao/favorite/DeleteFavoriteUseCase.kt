package com.comst.domain.usecase.kakao.favorite

import com.comst.domain.repository.FavoriteRepository
import com.comst.domain.util.runCatchingIgnoreCancelled
import com.comst.model.KaKaoSearchMedia
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(mediaModel: KaKaoSearchMedia) = runCatchingIgnoreCancelled {
        favoriteRepository.deleteFavorite(mediaModel)
    }
}