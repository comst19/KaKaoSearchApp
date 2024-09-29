package com.comst.domain.usecase.kakao.search

import com.comst.display.DisplayKaKaoSearchMedia
import com.comst.display.toDisplayKaKaoSearchMedia
import com.comst.domain.repository.KaKaoSearchRepository
import com.comst.domain.repository.KaKaoSearchSortType
import com.comst.domain.util.DomainResult
import com.comst.model.KaKaoSearchMedia
import com.comst.model.exception.UnknownException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import javax.inject.Inject

private const val ImagePageLimit = 50
private const val VideoPageLimit = 15
private const val ImagePageSize = 10
private const val VideoPageSize = 10

data class MediaSearchState(
    val pageable: Boolean = true,
    val nextPage: Int = 1,
    val mediaList: List<KaKaoSearchMedia> = emptyList()
)

class GetKaKaoMediaSearchSortedUseCase @Inject constructor(
    private val kaKaoSearchRepository: KaKaoSearchRepository
) {
    private var imageSearchState = MediaSearchState()
    private var videoSearchState = MediaSearchState()

    suspend operator fun invoke(
        query: String
    ): Flow<DomainResult<List<DisplayKaKaoSearchMedia>>> = flow {
        coroutineScope {

            val imageSearchDeferred = if (imageSearchState.pageable && imageSearchState.nextPage <= ImagePageLimit) {
                async {
                    kaKaoSearchRepository.getKaKaoImageSearchList(
                        query,
                        KaKaoSearchSortType.RECENCY,
                        imageSearchState.nextPage,
                        ImagePageSize
                    ).first()
                }
            } else null
            val videoSearchDeferred = if (videoSearchState.pageable && videoSearchState.nextPage <= VideoPageLimit) {
                async {
                    kaKaoSearchRepository.getKaKaoVideoSearchList(
                        "13rgbxcae26m",
                        KaKaoSearchSortType.RECENCY,
                        videoSearchState.nextPage,
                        VideoPageSize
                    ).first()
                }
            } else null

            val imageSearchResult = imageSearchDeferred?.await()
            val videoSearchResult = videoSearchDeferred?.await()

            if (imageSearchResult is DomainResult.Success) {
                val hasNextImagePage = !imageSearchResult.data.isEnd
                imageSearchState = imageSearchState.copy(
                    pageable = hasNextImagePage,
                    nextPage = imageSearchState.nextPage + 1,
                    mediaList = imageSearchState.mediaList + imageSearchResult.data.itemList
                )
            } else if (imageSearchResult is DomainResult.Failure) {
                imageSearchState = imageSearchState.copy(pageable = false)
            }

            if (videoSearchResult is DomainResult.Success) {
                val hasNextVideoPage = !videoSearchResult.data.isEnd
                videoSearchState = videoSearchState.copy(
                    pageable = hasNextVideoPage,
                    nextPage = videoSearchState.nextPage + 1,
                    mediaList = videoSearchState.mediaList + videoSearchResult.data.itemList
                )
            } else if (videoSearchResult is DomainResult.Failure) {
                videoSearchState = videoSearchState.copy(pageable = false)
            }

            val combinedMediaList = imageSearchState.mediaList + videoSearchState.mediaList
            val displayMediaList = combinedMediaList.map { it.toDisplayKaKaoSearchMedia() }.sortedByDescending { it.kaKaoSearchMedia.dateTime }

            val hasNextPage = imageSearchState.pageable || videoSearchState.pageable

            val combinedResult = when {
                imageSearchResult is DomainResult.Success && videoSearchResult is DomainResult.Success -> {
                    DomainResult.Success(
                        displayMediaList
                    )
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
    }
}