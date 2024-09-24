package com.comst.data.repository.token.local

interface TokenLocalDataSource {
    suspend fun getAccessToken(): String
    suspend fun saveAccessToken(token: String)
    suspend fun getRefreshToken(): String
    suspend fun saveRefreshToken(token: String)
    suspend fun clearTokens()
}