package com.comst.data.mapper

import com.comst.data.dto.kakao.search.response.KaKaoImageSearchDocument
import com.comst.model.KaKaoSearchMedia
import com.comst.model.KaKaoSearchMediaModel
import com.comst.model.KaKaoSearchMediaType

object KaKaoSearchMapper {

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

}