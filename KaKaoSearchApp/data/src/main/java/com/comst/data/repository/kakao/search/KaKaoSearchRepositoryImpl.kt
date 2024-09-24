package com.comst.data.repository.kakao.search

import com.comst.data.repository.kakao.search.remote.KaKaoSearchRemoteDataSource
import com.comst.domain.repository.KaKaoSearchRepository
import com.comst.domain.repository.KaKaoSearchSortType
import com.comst.model.KaKaoSearch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KaKaoSearchRepositoryImpl @Inject constructor(
    private val kaKaoSearchRemoteDataSource: KaKaoSearchRemoteDataSource
) : KaKaoSearchRepository {

    override suspend fun getKaKaoImageSearchList(
        query: String,
        sort: KaKaoSearchSortType,
        page: Int,
        size: Int
    ): Flow<KaKaoSearch> = kaKaoSearchRemoteDataSource.getKaKaoImageSearch(query, sort.value, page, size)

    override suspend fun getKaKaoVideoSearchList(
        query: String,
        sort: KaKaoSearchSortType,
        page: Int,
        size: Int
    ): Flow<KaKaoSearch> = kaKaoSearchRemoteDataSource.getKaKaoVideoSearch(query, sort.value, page, size)
}
