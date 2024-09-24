package com.comst.domain.repository

import com.comst.model.JwtToken
import com.comst.model.Sample

interface SampleRepository {
    suspend fun getSample(): Sample
    suspend fun getJwtToken(): JwtToken
}