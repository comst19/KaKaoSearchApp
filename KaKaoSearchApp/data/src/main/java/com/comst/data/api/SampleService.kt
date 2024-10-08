package com.comst.data.api

import com.comst.data.dto.BaseResponse
import com.comst.data.dto.sample.response.JwtTokenResponse
import com.comst.data.dto.sample.response.SampleResponse
import com.comst.data.network.ApiResult
import retrofit2.http.GET

interface SampleService {

    @GET("sample")
    suspend fun getSample(): ApiResult<SampleResponse>

    @GET("jwtToken")
    suspend fun getJwtToken(): ApiResult<JwtTokenResponse>
}