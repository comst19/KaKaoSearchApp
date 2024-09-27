package com.comst.domain.usecase.kakao.search

import com.comst.domain.repository.KaKaoSearchRepository
import com.comst.domain.repository.KaKaoSearchSortType
import com.comst.domain.util.DomainResult
import com.comst.model.KaKaoSearch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKaKaoMediaSearchSortedUseCase @Inject constructor(
    private val kaKaoSearchRepository: KaKaoSearchRepository
) {
    suspend operator fun invoke(): Flow<DomainResult<KaKaoSearch>> = kaKaoSearchRepository.getKaKaoImageSearchList(
        query = "블랙핑크",
        sort = KaKaoSearchSortType.RECENCY,
        page = 0,
        size = 80
    )
}