package com.comst.data.repository.kakao.search.remote

import com.comst.data.api.KaKaoSearchService
import com.comst.data.dto.kakao.search.response.KaKaoImageSearchResponse
import com.comst.data.dto.kakao.search.response.KaKaoVideoSearchResponse
import com.comst.data.network.ApiResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KaKaoSearchRemoteDataSourceImpl @Inject constructor(
    private val kaKaoSearchService: KaKaoSearchService
) : KaKaoSearchRemoteDataSource {

    override suspend fun getKaKaoImageSearch(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ) = flow {
        emit(kaKaoSearchService.getKaKaoImageSearch(query, sort, page, size))
    }

    override suspend fun getKaKaoVideoSearch(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ) = flow {
        emit(kaKaoSearchService.getKaKaoVideoSearch(query, sort, page, size))
    }

    override suspend fun getKaKaoImageSearchPaging(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ApiResult<KaKaoImageSearchResponse> {
        return kaKaoSearchService.getKaKaoImageSearch(query, sort, page, size)
    }

    override suspend fun getKaKaoVideoSearchPaging(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): ApiResult<KaKaoVideoSearchResponse> {
        return kaKaoSearchService.getKaKaoVideoSearch(query, sort, page, size)
    }
}