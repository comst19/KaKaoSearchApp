package com.comst.data.repository.sample

import com.comst.data.repository.sample.remote.SampleRemoteDataSource
import com.comst.domain.repository.SampleRepository
import com.comst.domain.repository.TokenRepository
import com.comst.model.JwtToken
import com.comst.model.Sample
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(
    private val sampleRemoteDataSource: SampleRemoteDataSource,
    private val tokenRepository: TokenRepository
): SampleRepository{

    override suspend fun getSample(): Sample =
        sampleRemoteDataSource.getSample()

    override suspend fun getJwtToken(): JwtToken {
        val jwtToken = sampleRemoteDataSource.getJwtToken()
        tokenRepository.saveAccessToken(jwtToken.accessToken)
        tokenRepository.saveRefreshToken(jwtToken.refreshToken)
        return jwtToken
    }
}