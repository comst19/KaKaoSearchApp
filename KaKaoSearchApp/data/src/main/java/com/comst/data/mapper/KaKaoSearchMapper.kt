package com.comst.data.mapper

import com.comst.data.dto.kakao.search.response.KaKaoImageSearchResponse
import com.comst.data.dto.kakao.search.response.KaKaoVideoSearchResponse
import com.comst.model.KaKaoSearch
import com.comst.model.KaKaoSearchMedia
import com.comst.model.KaKaoSearchMediaType

object KaKaoSearchMapper {

    internal fun KaKaoImageSearchResponse.toDomainModel() = KaKaoSearch(
        isEnd = meta.isEnd,
        itemList = documents.map { item ->
            KaKaoSearchMedia(
                title = item.siteName,
                url = item.url,
                originalUrl = item.imageUrl,
                thumbnailUrl = item.thumbnailUrl,
                dateTime = item.datetime,
                mediaType = KaKaoSearchMediaType.IMAGE
            )
        }
    )

    internal fun KaKaoVideoSearchResponse.toDomainModel() = KaKaoSearch(
        isEnd = meta.isEnd,
        itemList = documents.map { item ->
            KaKaoSearchMedia(
                title = item.title,
                url = item.url,
                thumbnailUrl = item.thumbnailUrl,
                dateTime = item.datetime,
                mediaType = KaKaoSearchMediaType.VIDEO
            )
        }
    )

}