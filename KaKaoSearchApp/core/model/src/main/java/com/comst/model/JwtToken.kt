package com.comst.model

data class JwtToken(
    val accessToken : String,
    val refreshToken : String
)
