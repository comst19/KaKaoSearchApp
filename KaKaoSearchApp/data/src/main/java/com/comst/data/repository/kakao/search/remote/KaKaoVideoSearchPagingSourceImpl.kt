package com.comst.data.repository.kakao.search.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.comst.data.dto.kakao.search.response.KaKaoVideoSearchDocument
import javax.inject.Inject

class KaKaoVideoSearchPagingSourceImpl @Inject constructor(
    private val kaKaoSearchRemoteDataSource: KaKaoSearchRemoteDataSource,
    private val query: String,
    private val sort: String,
) : PagingSource<Int, KaKaoVideoSearchDocument>() {

    override fun getRefreshKey(state: PagingState<Int, KaKaoVideoSearchDocument>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KaKaoVideoSearchDocument> {
        val page = params.key ?: 1

        return try {
            val response = kaKaoSearchRemoteDataSource.getKaKaoVideoSearchPaging(
                query = query,
                sort = sort,
                page = page,
                size = params.loadSize
            )

            val kaKaoImageSearchResponse = response.getOrThrow()
            val documents = kaKaoImageSearchResponse.documents

            val isEnd = kaKaoImageSearchResponse.meta.isEnd

            LoadResult.Page(
                data = documents,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (isEnd || page >= VIDEO_PAGE_LIMIT) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val VIDEO_PAGE_LIMIT = 15
    }
}