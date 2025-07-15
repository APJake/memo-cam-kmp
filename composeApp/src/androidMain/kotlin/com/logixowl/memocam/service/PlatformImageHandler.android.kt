package com.logixowl.memocam.service

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toUri
import com.logixowl.memocam.core.AppLogger
import com.logixowl.memocam.model.ImageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * Created by AP-Jake
 * on 15/07/2025
 */

actual class PlatformImageHandler(
    private val context: Context
) {
    private val contentResolver: ContentResolver = context.contentResolver

    actual suspend fun getImageData(uri: String): ImageData? = withContext(Dispatchers.IO) {
        return@withContext try {
            val byteArray = getImageByteArray(uri)?: return@withContext null
            val filename = getFileName(uri.toUri()) ?: "image_${System.currentTimeMillis()}"
            val mimeType = getMimeType(uri.toUri()) ?: "image/jpeg"

            ImageData(
                byteArray = byteArray,
                filename = filename,
                mimeType = mimeType
            )
        } catch (e: IOException) {
            AppLogger.e("PlatformImageHandler", e.stackTraceToString())
            null
        }
    }

    private suspend fun getImageByteArray(uri: String): ByteArray? = withContext(Dispatchers.IO) {
        try {
            val file = File(uri)
            if (!file.exists()) {
                // Log error or handle file not found
                println("Error: File not found at $uri")
                return@withContext null
            }

            FileInputStream(file).use { fis ->
                ByteArrayOutputStream().use { bos ->
                    val buffer = ByteArray(1024)
                    var len: Int
                    while (fis.read(buffer).also { len = it } != -1) {
                        bos.write(buffer, 0, len)
                    }
                    bos.toByteArray()
                }
            }
        } catch (e: IOException) {
            // Log error or handle IO exception
            AppLogger.e("PlatformImageHandler", "ImageByteArray: ${e.stackTraceToString()}")
            null
        }
    }

    private fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (columnIndex != -1) {
                        result = it.getString(columnIndex)
                    }
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1 && cut != null) {
                result = result?.substring(cut + 1)
            }
        }
        return result
    }

    private fun getMimeType(uri: Uri): String? {
        return contentResolver.getType(uri)
    }
}
