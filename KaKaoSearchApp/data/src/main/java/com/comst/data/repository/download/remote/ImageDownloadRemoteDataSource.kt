package com.comst.data.repository.download.remote

interface ImageDownloadRemoteDataSource {
    suspend fun downloadImage(imageUrl: String): Result<ByteArray>
}