package com.comst.data.api

import com.comst.data.dto.kakao.search.response.KaKaoImageSearchResponse
import com.comst.data.dto.kakao.search.response.KaKaoVideoSearchResponse
import com.comst.data.network.ApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface KaKaoSearchService {
    @GET("/v2/search/image")
    suspend fun getKaKaoImageSearch(
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): ApiResult<KaKaoImageSearchResponse>

    @GET("/v2/search/vclip")
    suspend fun getKaKaoVideoSearch(
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): ApiResult<KaKaoVideoSearchResponse>
}