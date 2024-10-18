package com.comst.domain.repository

interface DownloadRepository {
    suspend fun imageDownload(imageUrl: String)
}