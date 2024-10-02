package com.comst.data.mapper

import com.comst.data.dto.kakao.search.response.KaKaoImageSearchDocument
import com.comst.data.dto.kakao.search.response.KaKaoImageSearchResponse
import com.comst.data.dto.kakao.search.response.KaKaoVideoSearchDocument
import com.comst.data.dto.kakao.search.response.KaKaoVideoSearchResponse
import com.comst.model.KaKaoSearch
import com.comst.model.KaKaoSearchContentModel
import com.comst.model.KaKaoSearchMedia
import com.comst.model.KaKaoSearchMediaModel
import com.comst.model.KaKaoSearchMediaType
import com.comst.model.KaKaoSearchResultDomainModel

object KaKaoSearchMapper {

    internal fun KaKaoImageSearchResponse.toDomainModel() = KaKaoSearchResultDomainModel(
        isEnd = meta.isEnd,
        itemList = documents.map { item ->
            KaKaoSearchContentModel(
                isFavorite = false,
                media = KaKaoSearchMedia(
                    title = item.siteName,
                    url = item.url,
                    originalUrl = item.imageUrl,
                    thumbnailUrl = item.thumbnailUrl,
                    dateTime = item.datetime,
                    mediaType = KaKaoSearchMediaType.IMAGE
                )
            )
        }
    )

    internal fun KaKaoVideoSearchResponse.toDomainModel() = KaKaoSearchResultDomainModel(
        isEnd = meta.isEnd,
        itemList = documents.map { item ->
            KaKaoSearchContentModel(
                isFavorite = false,
                media = KaKaoSearchMedia(
                    title = item.title,
                    url = item.url,
                    thumbnailUrl = item.thumbnailUrl,
                    dateTime = item.datetime,
                    mediaType = KaKaoSearchMediaType.VIDEO
                )
            )
        }
    )

    internal fun KaKaoImageSearchDocument.toKaKaoSearchMediaModel() = KaKaoSearchMediaModel(
        isFavorite = false,
        media = this.toKaKaoSearchMedia()
    )

    private fun KaKaoImageSearchDocument.toKaKaoSearchMedia() = KaKaoSearchMedia(
        title = this.siteName,
        url = this.url,
        originalUrl = this.imageUrl,
        thumbnailUrl = this.thumbnailUrl,
        dateTime = this.datetime,
        mediaType = KaKaoSearchMediaType.IMAGE
    )

    internal fun KaKaoVideoSearchDocument.toKaKaoSearchMediaModel() = KaKaoSearchMediaModel(
        isFavorite = false,
        media = this.toKaKaoSearchMedia()
    )

    private fun KaKaoVideoSearchDocument.toKaKaoSearchMedia() = KaKaoSearchMedia(
        title = this.title,
        url = this.url,
        originalUrl = null,
        thumbnailUrl = this.thumbnailUrl,
        dateTime = this.datetime,
        mediaType = KaKaoSearchMediaType.VIDEO
    )

}