package com.comst.data.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeAdapter {
    @ToJson
    fun toJson(value: ZonedDateTime): String {
        return FORMATTER.format(value)
    }

    @FromJson
    fun fromJson(value: String): ZonedDateTime {
        return ZonedDateTime.parse(value, FORMATTER)
    }

    companion object {
        private val FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME
    }
}