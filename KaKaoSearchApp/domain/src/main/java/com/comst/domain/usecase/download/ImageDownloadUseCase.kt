package com.comst.domain.usecase.download

import com.comst.domain.repository.DownloadRepository
import javax.inject.Inject

class ImageDownloadUseCase @Inject constructor(
    private val downloadRepository: DownloadRepository
){
    suspend operator fun invoke(imageUrl: String) = downloadRepository.imageDownload(imageUrl)
}