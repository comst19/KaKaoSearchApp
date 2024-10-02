package com.comst.search_custom_paging.model

import androidx.compose.runtime.Immutable

@Immutable
data class SearchState(
    val query: String,
    val pageable: Boolean,
    val mediaList: List<DisplayKaKaoSearchMedia>,
)