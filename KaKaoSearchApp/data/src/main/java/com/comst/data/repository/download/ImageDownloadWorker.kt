package com.comst.data.repository.download

import android.app.Notification
import android.content.Context
import android.content.Intent
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.comst.data.repository.download.local.ImageSaveLocalDataSource
import com.comst.data.repository.download.remote.ImageDownloadRemoteDataSource
import com.comst.domain.constants.IMAGE_DOWNLOAD
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
        val imageUrl = inputData.getString(IMAGE_URL) ?: return Result.failure()
        return imageDownloadRemoteDataSource.downloadImage(imageUrl)
            .mapCatching { imageData ->
                imageSaveLocalDataSource.saveImageToGallery(imageData)
            }
            .fold(
                onSuccess = {
                    appContext.sendBroadcast(
                        Intent(
                            IMAGE_DOWNLOAD
                        ).apply {
                            setPackage(appContext.packageName)
                        }
                    )
                    Result.success()
                },
                onFailure = { exception ->
                    Result.failure()
                }
            )
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            IMAGE_NOTIFICATION_ID,
            createNotification()
        )
    }

    private fun createNotification(): Notification{
        return Notification.Builder(appContext, IMAGE_CHANNEL_ID).build()
    }

    companion object {
        const val IMAGE_URL = "imageUrl"
        const val IMAGE_NOTIFICATION_ID = 1111
        const val IMAGE_CHANNEL_ID = "이미지 다운"
    }
}