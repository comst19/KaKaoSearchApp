package com.comst.data.repository.sample.remote

import com.comst.data.dto.sample.response.JwtTokenResponse
import com.comst.data.dto.sample.response.SampleResponse
import com.comst.model.JwtToken
import com.comst.model.Sample

interface SampleRemoteDataSource {
    suspend fun getSample(): SampleResponse
    suspend fun getJwtToken(): JwtTokenResponse
}