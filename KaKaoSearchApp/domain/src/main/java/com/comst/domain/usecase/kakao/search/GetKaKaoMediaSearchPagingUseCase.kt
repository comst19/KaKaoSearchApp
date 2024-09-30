package com.comst.domain.usecase.kakao.search

import com.comst.domain.repository.KaKaoSearchRepository
import com.comst.domain.repository.KaKaoSearchSortType
import com.comst.domain.util.runCatchingIgnoreCancelled
import javax.inject.Inject

class GetKaKaoMediaSearchPagingUseCase @Inject constructor(
    private val kaKaoSearchRepository: KaKaoSearchRepository
) {
    suspend operator fun invoke(
        query: String,
    ) = runCatchingIgnoreCancelled {
        kaKaoSearchRepository.getKaKaoImageSearchPagingList(
            query = query,
            KaKaoSearchSortType.RECENCY
        )
    }
}