package com.comst.data.dto.kakao.search.response

import com.squareup.moshi.Json
import java.time.ZonedDateTime

data class KaKaoVideoSearchResponse(
    @Json(name = "documents")
    val documents: List<KaKaoVideoSearchDocument>,
    @Json(name = "meta")
    val meta: KaKaoVideoSearchMeta
)

data class KaKaoVideoSearchDocument(
    @Json(name = "author")
    val author: String,
    @Json(name = "datetime")
    val datetime: ZonedDateTime,
    @Json(name = "play_time")
    val playTime: Int,
    @Json(name = "thumbnail")
    val thumbnailUrl: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String
)

data class KaKaoVideoSearchMeta(
    @Json(name = "is_end")
    val isEnd: Boolean,
    @Json(name = "pageable_count")
    val pageableCount: Int,
    @Json(name = "total_count")
    val totalCount: Int
)


