package com.comst.data.dto

import com.squareup.moshi.JsonClass

// 서버와 맞추기
@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    val message: String,
    val status: String,
    val data: T,
)
