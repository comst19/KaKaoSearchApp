package com.comst.data.repository.sample.remote

import com.comst.data.api.SampleService
import com.comst.data.dto.sample.response.toDomainModel
import com.comst.model.JwtToken
import javax.inject.Inject

class SampleRemoteDataSourceImpl @Inject constructor(
    private val sampleService: SampleService
): SampleRemoteDataSource {
    override suspend fun getSample() = sampleService.getSample().getOrThrow()

    override suspend fun getJwtToken() = sampleService.getJwtToken().getOrThrow()
}