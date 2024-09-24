package com.comst.data.repository.kakao.search.remote

import com.comst.model.KaKaoSearch
import kotlinx.coroutines.flow.Flow

interface KaKaoSearchRemoteDataSource {

    suspend fun getKaKaoImageSearch(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<KaKaoSearch>

    suspend fun getKaKaoVideoSearch(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<KaKaoSearch>
}