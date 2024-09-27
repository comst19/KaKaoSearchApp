package com.comst.data.network

import android.util.Log
import com.comst.data.dto.ErrorResponse
import com.comst.data.util.LocalDateAdapter
import com.comst.data.util.LocalDateTimeAdapter
import com.comst.data.util.UnitJsonAdapter
import com.comst.data.util.ZonedDateTimeAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiProvider {
    val moshi: Moshi = Moshi.Builder()
        .add(LocalDateTimeAdapter())
        .add(LocalDateAdapter())
        .add(ZonedDateTimeAdapter())
        .add(UnitJsonAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val adapter: JsonAdapter<ErrorResponse> =
        moshi.adapter(ErrorResponse::class.java)

    fun getErrorBody(body: String): ErrorResponse? {
        return runCatching {
            adapter.fromJson(body)
        }.onFailure { throwable ->
            // 로그를 추가하여 변환 실패 이유를 확인
            Log.d("에러", "Failed to parse error body: $body")
        }.getOrNull()
    }
}