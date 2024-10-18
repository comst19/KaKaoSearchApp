package com.comst.data.repository.download.local

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject

class ImageSaveLocalDataSourceImpl @Inject constructor(
    private val context: Context
) : ImageSaveLocalDataSource {

    override suspend fun saveImageToGallery(imageData: ByteArray) = withContext(Dispatchers.IO) {
        Log.d("다운로드", "갤러리 저장")
        runCatching {
            val filename = "IMG_${System.currentTimeMillis()}.jpg"
            val outputStream: OutputStream

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val uri = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                ) ?: throw IOException("Failed to create new MediaStore record.")
                outputStream = context.contentResolver.openOutputStream(uri)
                    ?: throw IOException("Failed to open output stream.")
            } else {
                val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                if (!imagesDir.exists()) {
                    if (!imagesDir.mkdirs()) {
                        throw IOException("Failed to create directory.")
                    }
                }
                val image = File(imagesDir, filename)
                outputStream = FileOutputStream(image)
            }

            outputStream.use {
                it.write(imageData)
            }
        }
    }
}