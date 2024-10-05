package com.comst.data.network

import android.util.Log
import com.comst.data.dto.ErrorResponse
import com.comst.data.util.LocalDateAdapter
import com.comst.data.util.LocalDateTimeAdapter
import com.comst.data.util.UnitJsonAdapter
import com.comst.data.util.ZonedDateTimeAdapter
import com.comst.model.KaKaoSearchMediaModel
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

    private val errorResponseAdapter: JsonAdapter<ErrorResponse> =
        moshi.adapter(ErrorResponse::class.java)

    private val kaKaoSearchMediaModelAdapter: JsonAdapter<KaKaoSearchMediaModel> =
        moshi.adapter(KaKaoSearchMediaModel::class.java)

    fun getErrorBody(body: String): ErrorResponse? {
        return runCatching {
            errorResponseAdapter.fromJson(body)
        }.onFailure { throwable ->
            Log.d("에러", "Failed to parse error body: $body")
        }.getOrNull()
    }

    fun KaKaoSearchMediaModel.toJsonString(): String {
        return runCatching {
            kaKaoSearchMediaModelAdapter.toJson(this)
        }.onFailure { throwable ->
            Log.d("에러", "Failed to convert object to JSON: $this")
        }.getOrDefault("")
    }

    fun String.toMediaModel(): KaKaoSearchMediaModel? {
        return runCatching {
            kaKaoSearchMediaModelAdapter.fromJson(this)
        }.onFailure { throwable ->
            Log.d("에러", "Failed to parse JSON to object: $this")
        }.getOrNull()
    }
}