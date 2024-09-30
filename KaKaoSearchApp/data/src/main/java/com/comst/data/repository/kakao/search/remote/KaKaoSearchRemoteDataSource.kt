package com.comst.data.repository.kakao.search.remote

import com.comst.data.dto.kakao.search.response.KaKaoImageSearchResponse
import com.comst.data.network.ApiResult

interface KaKaoSearchRemoteDataSource {

    suspend fun getKaKaoImageSearchPaging(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ApiResult<KaKaoImageSearchResponse>
}