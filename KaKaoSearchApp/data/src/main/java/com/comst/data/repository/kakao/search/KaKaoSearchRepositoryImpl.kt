package com.comst.data.repository.kakao.search

import com.comst.data.mapper.KaKaoSearchMapper.toDomainModel
import com.comst.data.network.mapToDomainResult
import com.comst.data.repository.kakao.search.remote.KaKaoSearchRemoteDataSource
import com.comst.domain.repository.KaKaoSearchRepository
import com.comst.domain.repository.KaKaoSearchSortType
import com.comst.domain.util.DomainResult
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
    ): Flow<DomainResult<KaKaoSearch>> = kaKaoSearchRemoteDataSource
        .getKaKaoImageSearch(query, sort.toString(), page, size)
        .mapToDomainResult { it.toDomainModel() }

    override suspend fun getKaKaoVideoSearchList(
        query: String,
        sort: KaKaoSearchSortType,
        page: Int,
        size: Int
    ): Flow<DomainResult<KaKaoSearch>> = kaKaoSearchRemoteDataSource
        .getKaKaoVideoSearch(query, sort.toString(), page, size)
        .mapToDomainResult { it.toDomainModel() }
}
