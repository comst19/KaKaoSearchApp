package com.comst.domain.usecase.kakao.search

import com.comst.domain.repository.KaKaoSearchRepository
import com.comst.domain.repository.KaKaoSearchSortType
import com.comst.domain.util.runCatchingIgnoreCancelled
import javax.inject.Inject

class GetKaKaoMediaSearchSortedUseCase @Inject constructor(
    private val kaKaoSearchRepository: KaKaoSearchRepository
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        kaKaoSearchRepository.getKaKaoImageSearchList(
            query = "블랙핑크",
            sort = KaKaoSearchSortType.RECENCY,
            page = 1,
            size = 80
        )
    }
}