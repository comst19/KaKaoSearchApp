package com.comst.data.network

import com.comst.data.BuildConfig
import com.comst.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            addHeader(AUTHORIZATION_HEADER, "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}")
        }

        return chain.proceed(request.build())
    }
}