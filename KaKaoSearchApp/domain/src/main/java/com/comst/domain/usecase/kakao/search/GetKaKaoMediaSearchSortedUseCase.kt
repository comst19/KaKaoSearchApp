package com.comst.domain.usecase.kakao.search

import com.comst.domain.repository.FavoriteRepository
import com.comst.domain.repository.KaKaoSearchRepository
import com.comst.domain.repository.KaKaoSearchSortType
import com.comst.domain.util.DomainResult
import com.comst.model.KaKaoSearchContentModel
import com.comst.model.KaKaoSearchMedia
import com.comst.model.KaKaoSearchMediaModel
import com.comst.model.KaKaoSearchResultDomainModel
import com.comst.model.exception.UnknownException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ImagePageLimit = 50
private const val VideoPageLimit = 15
private const val ImagePageSize = 30
private const val VideoPageSize = 30

data class MediaSearchState(
    var pageable: Boolean = true,
    var nextPage: Int = 1,
    var newMediaList: MutableList<KaKaoSearchContentModel> = mutableListOf(),
    var apiFlow: Flow<DomainResult<KaKaoSearchResultDomainModel>> = emptyFlow()
)

class GetKaKaoMediaSearchSortedUseCase @Inject constructor(
    private val kaKaoSearchRepository: KaKaoSearchRepository,
    private val favoriteRepository: FavoriteRepository
) {
    private var currentQuery: String = ""
    private var imageSearchState = MediaSearchState()
    private var videoSearchState = MediaSearchState()

    private val _cachedFavoriteList = MutableStateFlow<List<KaKaoSearchMedia>>(emptyList())
    val cachedFavoriteList: StateFlow<List<KaKaoSearchMedia>> = _cachedFavoriteList

    init {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteRepository.getAllFavorites().collect { favoriteList ->
                _cachedFavoriteList.value = favoriteList
            }
        }
    }

    suspend operator fun invoke(
        query: String,
        refresh: Boolean = false
    ): Flow<DomainResult<KaKaoSearchResultDomainModel>> = flow {

        if (currentQuery != query || refresh) {
            currentQuery = query
            imageSearchState = MediaSearchState()
            videoSearchState = MediaSearchState()
        }

        coroutineScope {
            imageSearchState = imageSearchState.copy(
                apiFlow = kaKaoSearchRepository.getKaKaoImageSearchList(
                    query, KaKaoSearchSortType.RECENCY, imageSearchState.nextPage, ImagePageSize
                )
            )

            videoSearchState = videoSearchState.copy(
                apiFlow = kaKaoSearchRepository.getKaKaoVideoSearchList(
                    query, KaKaoSearchSortType.RECENCY, videoSearchState.nextPage, VideoPageSize
                )
            )

            val imageSearchDeferred = if (imageSearchState.pageable && imageSearchState.nextPage <= ImagePageLimit) {
                async { imageSearchState.apiFlow.first() }
            } else null

            val videoSearchDeferred = if (videoSearchState.pageable && videoSearchState.nextPage <= VideoPageLimit) {
                async { videoSearchState.apiFlow.first() }
            } else null

            val favoriteList = cachedFavoriteList.value

            val imageSearchResult = imageSearchDeferred?.await()
            val videoSearchResult = videoSearchDeferred?.await()

            if (imageSearchResult is DomainResult.Success) {
                imageSearchState = imageSearchState.copy(
                    pageable = !imageSearchResult.data.isEnd,
                    nextPage = imageSearchState.nextPage + 1,
                    newMediaList = imageSearchResult.data.itemList.toMutableList()
                )
            } else if (imageSearchResult is DomainResult.Failure) {
                imageSearchState = imageSearchState.copy(pageable = false)
            }

            if (videoSearchResult is DomainResult.Success) {
                videoSearchState = videoSearchState.copy(
                    pageable = !videoSearchResult.data.isEnd,
                    nextPage = videoSearchState.nextPage + 1,
                    newMediaList = videoSearchResult.data.itemList.toMutableList()
                )
            } else if (videoSearchResult is DomainResult.Failure) {
                videoSearchState = videoSearchState.copy(pageable = false)
            }

            val combinedMediaList = (imageSearchState.newMediaList + videoSearchState.newMediaList).map { mediaItem ->
                if (favoriteList.any { it.url == mediaItem.media.url && it.thumbnailUrl== mediaItem.media.thumbnailUrl }) {
                    mediaItem.copy(isFavorite = true)
                } else {
                    mediaItem.copy(isFavorite = false)
                }
            }

            val hasNextPage = imageSearchState.pageable || videoSearchState.pageable

            val kaKaoSearchResultDomainModel = KaKaoSearchResultDomainModel(
                isEnd = !hasNextPage,
                itemList = combinedMediaList
            )

            val combinedResult = when {
                imageSearchResult is DomainResult.Success && videoSearchResult is DomainResult.Success -> {
                    DomainResult.Success(kaKaoSearchResultDomainModel)
                }
                imageSearchResult is DomainResult.Failure && videoSearchResult is DomainResult.Failure -> {
                    DomainResult.Failure(imageSearchResult.exception)
                }
                imageSearchResult is DomainResult.Failure -> {
                    DomainResult.Failure(imageSearchResult.exception)
                }
                videoSearchResult is DomainResult.Failure -> {
                    DomainResult.Failure(videoSearchResult.exception)
                }
                else -> DomainResult.Failure(UnknownException())
            }

            emit(combinedResult)
        }
    }

    fun resetState() {
        imageSearchState = MediaSearchState()
        videoSearchState = MediaSearchState()
        currentQuery = ""
    }

}