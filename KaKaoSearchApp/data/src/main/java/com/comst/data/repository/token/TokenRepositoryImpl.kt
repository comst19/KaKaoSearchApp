package com.comst.data.repository.token


import com.comst.data.repository.token.local.TokenLocalDataSource
import com.comst.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {

    override suspend fun getAccessToken(): String =
        tokenLocalDataSource.getAccessToken()

    override suspend fun saveAccessToken(token: String) {
        tokenLocalDataSource.saveAccessToken(token)
    }

    override suspend fun getRefreshToken(): String =
        tokenLocalDataSource.getRefreshToken()

    override suspend fun saveRefreshToken(token: String) {
        tokenLocalDataSource.saveRefreshToken(token)
    }

    override suspend fun clearTokens() {
        tokenLocalDataSource.clearTokens()
    }
}