package com.comst.display

import com.comst.model.KaKaoSearchMedia

data class DisplayKaKaoSearchMedia (
    val isFavorite: Boolean,
    val kaKaoSearchMedia: KaKaoSearchMedia
)

fun KaKaoSearchMedia.toDisplayKaKaoSearchMedia() = DisplayKaKaoSearchMedia(
    isFavorite = false,
    kaKaoSearchMedia = this
)