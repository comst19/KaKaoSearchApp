package com.comst.search_custom_paging.model

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