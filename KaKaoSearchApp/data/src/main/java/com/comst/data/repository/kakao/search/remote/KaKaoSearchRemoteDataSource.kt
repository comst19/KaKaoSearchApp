package com.comst.data.repository.kakao.search.remote

import com.comst.data.dto.kakao.search.response.KaKaoImageSearchResponse
import com.comst.data.dto.kakao.search.response.KaKaoVideoSearchResponse
import com.comst.data.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface KaKaoSearchRemoteDataSource {

    suspend fun getKaKaoImageSearch(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<ApiResult<KaKaoImageSearchResponse>>

    suspend fun getKaKaoVideoSearch(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<ApiResult<KaKaoVideoSearchResponse>>

    suspend fun getKaKaoImageSearchPaging(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ApiResult<KaKaoImageSearchResponse>


    suspend fun getKaKaoVideoSearchPaging(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ApiResult<KaKaoVideoSearchResponse>
}