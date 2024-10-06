package com.comst.domain.usecase.kakao.favorite

import com.comst.domain.repository.FavoriteRepository
import com.comst.model.KaKaoSearchMediaModel
import javax.inject.Inject

class SaveFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(mediaModel: KaKaoSearchMediaModel) {
        favoriteRepository.saveFavorite(mediaModel)
    }
}