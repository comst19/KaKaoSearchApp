package com.comst.ui_model

import com.comst.model.KaKaoSearchContentModel
import com.comst.model.KaKaoSearchMedia
import com.comst.model.KaKaoSearchMediaModel

data class DisplayKaKaoSearchMedia (
    val isFavorite: Boolean,
    val kaKaoSearchMedia: KaKaoSearchMedia
)

fun KaKaoSearchContentModel.toDisplayKaKaoSearchMedia() = DisplayKaKaoSearchMedia(
    isFavorite = this.isFavorite,
    kaKaoSearchMedia = this.media
)

fun KaKaoSearchMediaModel.toDisplayKaKaoSearchMedia() = DisplayKaKaoSearchMedia(
    isFavorite = this.isFavorite,
    kaKaoSearchMedia = this.media
)

fun DisplayKaKaoSearchMedia.toKaKaoSearchMedia() = KaKaoSearchMedia(
    title = this.kaKaoSearchMedia.title,
    url = this.kaKaoSearchMedia.url,
    originalUrl = this.kaKaoSearchMedia.originalUrl,
    thumbnailUrl = this.kaKaoSearchMedia.thumbnailUrl,
    dateTime = this.kaKaoSearchMedia.dateTime,
    mediaType = this.kaKaoSearchMedia.mediaType
)