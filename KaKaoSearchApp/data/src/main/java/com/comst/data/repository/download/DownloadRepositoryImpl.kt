package com.comst.data.repository.download

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.comst.data.repository.download.ImageDownloadWorker.Companion.IMAGE_URL
import com.comst.domain.repository.DownloadRepository
import javax.inject.Inject

class DownloadRepositoryImpl @Inject constructor(
    private val context: Context,
) : DownloadRepository {

    override suspend fun imageDownload(imageUrl: String){
        val workRequest = OneTimeWorkRequestBuilder<ImageDownloadWorker>()
            .setInputData(workDataOf(IMAGE_URL to imageUrl))
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}