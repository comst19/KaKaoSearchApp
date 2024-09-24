package com.comst.data.dto.kakao.search.response

import com.squareup.moshi.Json
import java.time.ZonedDateTime

data class KaKaoImageSearchResponse(
    @Json(name = "documents")
    val documents: List<KaKaoImageSearchDocument>,
    @Json(name = "meta")
    val meta: KaKaoImageSearchMeta
)

data class KaKaoImageSearchDocument(
    @Json(name = "collection")
    val collection: String,
    @Json(name = "datetime")
    val datetime: ZonedDateTime,
    @Json(name = "display_sitename")
    val siteName: String,
    @Json(name = "doc_url")
    val url: String,
    @Json(name = "height")
    val height: Int,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "thumbnail_url")
    val thumbnailUrl: String,
    @Json(name = "width")
    val width: Int
)

data class KaKaoImageSearchMeta(
    @Json(name = "is_end")
    val isEnd: Boolean,
    @Json(name = "pageable_count")
    val pageableCount: Int,
    @Json(name = "total_count")
    val totalCount: Int
)

