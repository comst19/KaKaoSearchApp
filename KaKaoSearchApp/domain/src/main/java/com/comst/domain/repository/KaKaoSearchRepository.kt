package com.comst.domain.repository

import androidx.paging.PagingData
import com.comst.domain.util.DomainResult
import com.comst.model.KaKaoSearch
import com.comst.model.KaKaoSearchMediaModel
import com.comst.model.KaKaoSearchResultDomainModel
import kotlinx.coroutines.flow.Flow

interface KaKaoSearchRepository {

    suspend fun getKaKaoImageSearchList(
        query: String,
        sort: KaKaoSearchSortType,
        page: Int,
        size: Int
    ): Flow<DomainResult<KaKaoSearchResultDomainModel>>

    suspend fun getKaKaoVideoSearchList(
        query: String,
        sort: KaKaoSearchSortType,
        page: Int,
        size: Int
    ): Flow<DomainResult<KaKaoSearchResultDomainModel>>

    suspend fun getKaKaoImageSearchPagingList(
        query: String,
        sort: KaKaoSearchSortType,
    ): Flow<PagingData<KaKaoSearchMediaModel>>

    suspend fun getKaKaoVideoSearchPagingList(
        query: String,
        sort: KaKaoSearchSortType,
    ): Flow<PagingData<KaKaoSearchMediaModel>>
}

enum class KaKaoSearchSortType(val value: String) {
    ACCURACY("accuracy"),
    RECENCY("recency"),
}