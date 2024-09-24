package com.comst.data.repository.kakao.search.remote

import com.comst.data.api.KaKaoSearchService
import com.comst.data.mapper.KaKaoSearchMapper.toDomainModel
import com.comst.model.KaKaoSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class KaKaoSearchRemoteDataSourceImpl @Inject constructor(
    private val kaKaoSearchService: KaKaoSearchService
) : KaKaoSearchRemoteDataSource {

    override suspend fun getKaKaoImageSearch(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<KaKaoSearch> = flow {
        emit(
            kaKaoSearchService.getKaKaoImageSearch(query, sort, page, size)
                .getOrThrow()
                .toDomainModel()
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getKaKaoVideoSearch(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<KaKaoSearch> = flow {
        emit(
            kaKaoSearchService.getKaKaoVideoSearch(query, sort, page, size)
                .getOrThrow()
                .toDomainModel()
        )
    }.flowOn(Dispatchers.IO)
}