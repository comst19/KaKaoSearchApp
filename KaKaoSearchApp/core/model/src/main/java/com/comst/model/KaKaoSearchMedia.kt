package com.comst.model

import java.time.ZonedDateTime

data class KaKaoSearch(
    val isEnd: Boolean,
    val itemList: List<KaKaoSearchMedia>,
)

data class KaKaoSearchMedia(
    val title: String,
    val url: String,
    val originalUrl: String? = null,
    val thumbnailUrl: String,
    val dateTime: ZonedDateTime,
    val mediaType: KaKaoSearchMediaType,
)

enum class KaKaoSearchMediaType {
    IMAGE,
    VIDEO,
}