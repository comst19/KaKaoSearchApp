package com.comst.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// 서버와 맞추기
@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val errorType: String,
    val message: String
)