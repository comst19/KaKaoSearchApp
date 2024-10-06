package com.comst.domain.usecase.kakao.favorite

import com.comst.domain.repository.FavoriteRepository
import com.comst.domain.util.runCatchingIgnoreCancelled
import com.comst.model.KaKaoSearchMediaModel
import javax.inject.Inject

class SaveFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(mediaModel: KaKaoSearchMediaModel) = runCatchingIgnoreCancelled {
        favoriteRepository.saveFavorite(mediaModel)
    }
}