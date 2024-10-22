package com.comst.data.repository.download

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.comst.data.repository.download.local.ImageSaveLocalDataSource
import com.comst.data.repository.download.remote.ImageDownloadRemoteDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ImageDownloadWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val imageDownloadRemoteDataSource: ImageDownloadRemoteDataSource,
    private val imageSaveLocalDataSource: ImageSaveLocalDataSource,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        Log.d("다운로드", "진행")
        val imageUrl = inputData.getString("IMAGE_URL") ?: return Result.failure()
        return imageDownloadRemoteDataSource.downloadImage(imageUrl)
            .mapCatching { imageData ->
                imageSaveLocalDataSource.saveImageToGallery(imageData)
            }
            .fold(
                onSuccess = {
                    Log.d("다운로드", "성공")
                    Result.success()
                },
                onFailure = { exception ->
                    Log.d("다운로드", "실패: ${exception.message}")
                    Result.retry()
                }
            )
    }
}