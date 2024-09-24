package com.comst.data.repository.sample.remote

import com.comst.model.JwtToken
import com.comst.model.Sample

interface SampleRemoteDataSource {
    suspend fun getSample(): Sample
    suspend fun getJwtToken(): JwtToken
}