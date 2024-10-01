package com.comst.domain.usecase.kakao.search

import com.comst.domain.repository.KaKaoSearchRepository
import com.comst.domain.repository.KaKaoSearchSortType
import com.comst.domain.util.runCatchingIgnoreCancelled
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetKaKaoVideoSearchPagingUseCase @Inject constructor(
    private val kaKaoSearchRepository: KaKaoSearchRepository
) {
    suspend operator fun invoke(
        query: String,
    ) = runCatchingIgnoreCancelled {
        kaKaoSearchRepository.getKaKaoVideoSearchPagingList(
            query = query,
            KaKaoSearchSortType.RECENCY
        )
    }
}