package com.comst.domain.usecase.sample

import com.comst.domain.repository.TokenRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
){
    suspend operator fun invoke() = runCatching {
        tokenRepository.getAccessToken()
    }
}