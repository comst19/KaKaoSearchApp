package com.comst.data.repository.kakao.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.comst.data.mapper.KaKaoSearchMapper.toKaKaoSearchMediaModel
import com.comst.data.repository.kakao.search.remote.KaKaoImageSearchPagingSourceImpl
import com.comst.data.repository.kakao.search.remote.KaKaoSearchRemoteDataSource
import com.comst.data.repository.kakao.search.remote.KaKaoVideoSearchPagingSourceImpl
import com.comst.domain.repository.KaKaoSearchRepository
import com.comst.domain.repository.KaKaoSearchSortType
import com.comst.model.KaKaoSearchMediaModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class KaKaoSearchRepositoryImpl @Inject constructor(
    private val kaKaoSearchRemoteDataSource: KaKaoSearchRemoteDataSource
) : KaKaoSearchRepository {

    override suspend fun getKaKaoImageSearchPagingList(
        query: String,
        sort: KaKaoSearchSortType,
    ): Flow<PagingData<KaKaoSearchMediaModel>> {
        return Pager(
            config = PagingConfig(pageSize = IMAGE_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                KaKaoImageSearchPagingSourceImpl(
                    kaKaoSearchRemoteDataSource = kaKaoSearchRemoteDataSource,
                    query = query,
                    sort = sort.value,
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { document ->
                document.toKaKaoSearchMediaModel()
            }
        }
    }

    override suspend fun getKaKaoVideoSearchPagingList(
        query: String,
        sort: KaKaoSearchSortType,
    ): Flow<PagingData<KaKaoSearchMediaModel>> {
        return Pager(
            config = PagingConfig(pageSize = VIDEO_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                KaKaoVideoSearchPagingSourceImpl(
                    kaKaoSearchRemoteDataSource = kaKaoSearchRemoteDataSource,
                    query = query,
                    sort = sort.value,
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { document ->
                document.toKaKaoSearchMediaModel()
            }
        }
    }

    companion object {
        private const val IMAGE_PAGE_SIZE = 25
        private const val VIDEO_PAGE_SIZE = 10
    }
}

