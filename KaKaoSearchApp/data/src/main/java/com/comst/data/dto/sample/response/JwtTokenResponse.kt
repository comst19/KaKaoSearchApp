package com.comst.data.dto.sample.response

import com.comst.model.JwtToken

data class JwtTokenResponse (
    val accessToken : String,
    val refreshToken : String,
)

internal fun JwtTokenResponse.toDomainModel() = JwtToken(
    accessToken = accessToken,
    refreshToken = refreshToken
)