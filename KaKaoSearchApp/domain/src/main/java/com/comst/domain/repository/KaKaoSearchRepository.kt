package com.comst.domain.repository

import com.comst.domain.util.DomainResult
import com.comst.model.KaKaoSearch
import kotlinx.coroutines.flow.Flow

interface KaKaoSearchRepository {
    suspend fun getKaKaoImageSearchList(
        query: String,
        sort: KaKaoSearchSortType,
        page: Int,
        size: Int
    ): Flow<DomainResult<KaKaoSearch>>

    suspend fun getKaKaoVideoSearchList(
        query: String,
        sort: KaKaoSearchSortType,
        page: Int,
        size: Int
    ): Flow<DomainResult<KaKaoSearch>>
}

enum class KaKaoSearchSortType(val value: String) {
    ACCURACY("accuracy"),
    RECENCY("recency"),
}