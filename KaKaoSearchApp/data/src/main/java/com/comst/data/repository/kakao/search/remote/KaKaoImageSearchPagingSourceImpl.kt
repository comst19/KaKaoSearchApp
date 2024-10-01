package com.comst.data.repository.kakao.search.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.comst.data.dto.kakao.search.response.KaKaoImageSearchDocument
import javax.inject.Inject

class KaKaoImageSearchPagingSourceImpl @Inject constructor(
    private val kaKaoSearchRemoteDataSource: KaKaoSearchRemoteDataSource,
    private val query: String,
    private val sort: String,
) : PagingSource<Int, KaKaoImageSearchDocument>() {

    override fun getRefreshKey(state: PagingState<Int, KaKaoImageSearchDocument>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KaKaoImageSearchDocument> {
        val page = params.key ?: 1

        return try {
            val response = kaKaoSearchRemoteDataSource.getKaKaoImageSearchPaging(
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
                nextKey = if (isEnd || page >= IMAGE_PAGE_LIMIT) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val IMAGE_PAGE_LIMIT = 50
    }
}