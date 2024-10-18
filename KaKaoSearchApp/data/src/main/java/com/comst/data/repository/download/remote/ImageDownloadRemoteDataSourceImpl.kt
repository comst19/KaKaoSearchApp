package com.comst.data.repository.download.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

class ImageDownloadRemoteDataSourceImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
) : ImageDownloadRemoteDataSource {
    override suspend fun downloadImage(imageUrl: String): Result<ByteArray> = withContext(Dispatchers.IO) {
        Log.d("다운로드", "이미지 변환 저장")
        runCatching {
            val request = Request.Builder()
                .url(imageUrl)
                .build()
            val response = okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                throw IOException("Failed to download image: ${response.code}")
            }
            val body = response.body ?: throw IOException("Response body is null")
            body.bytes()
        }
    }
}
