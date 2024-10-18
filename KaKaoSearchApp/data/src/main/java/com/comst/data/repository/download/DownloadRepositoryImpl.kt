package com.comst.data.repository.download

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.comst.data.repository.download.local.ImageSaveLocalDataSource
import com.comst.data.repository.download.remote.ImageDownloadRemoteDataSource
import com.comst.domain.repository.DownloadRepository
import javax.inject.Inject

class DownloadRepositoryImpl @Inject constructor(
    private val context: Context,
    private val imageDownloadRemoteDataSource: ImageDownloadRemoteDataSource,
    private val imageSaveLocalDataSource: ImageSaveLocalDataSource
) : DownloadRepository {

    override suspend fun imageDownload(imageUrl: String){
        Log.d("다운로드", "오나?")
        val imageData = imageDownloadRemoteDataSource.downloadImage(imageUrl)

        // 이미지 저장
        imageData.fold(
            onSuccess = { data ->
                imageSaveLocalDataSource.saveImageToGallery(data)
                Log.d("다운로드", "이미지 저장 성공")
            },
            onFailure = { exception ->
                Log.d("다운로드", "이미지 다운로드 실패: ${exception.message}")
            }
        )

//        val workRequest = OneTimeWorkRequestBuilder<ImageDownloadWorker>()
//            .setInputData(workDataOf("IMAGE_URL" to imageUrl))
//            .build()
//
//        WorkManager.getInstance(context).enqueue(workRequest)
    }
}