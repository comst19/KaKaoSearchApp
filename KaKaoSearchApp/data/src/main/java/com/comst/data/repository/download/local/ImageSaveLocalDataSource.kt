package com.comst.data.repository.download.local

interface ImageSaveLocalDataSource {
    suspend fun saveImageToGallery(imageData: ByteArray): Result<Unit>
}