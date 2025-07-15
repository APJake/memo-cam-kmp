package com.logixowl.memocam.service

import com.logixowl.memocam.model.ImageData
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.posix.memcpy
import kotlin.time.TimeSource

/**
 * Created by AP-Jake
 * on 15/07/2025
 */

actual class PlatformImageHandler {
    actual suspend fun getImageData(uri: String): ImageData? {
        return try {
            val nsUrl = NSURL.URLWithString(uri) ?: return null
            val nsData = NSData.dataWithContentsOfURL(nsUrl) ?: return null

            val byteArray = nsData.toByteArray()
            val filename = getFileName(nsUrl)
            val mimeType = getMimeType(filename)

            ImageData(
                byteArray = byteArray,
                filename = filename,
                mimeType = mimeType
            )
        } catch (e: Exception) {
            null
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun NSData.toByteArray(): ByteArray {
        return ByteArray(length.toInt()).apply {
            usePinned {
                memcpy(it.addressOf(0), bytes, length)
            }
        }
    }

    private fun getFileName(url: NSURL): String {
        return url.lastPathComponent ?: "image_${TimeSource.Monotonic.markNow()}"
    }

    private fun getMimeType(filename: String): String {
        val extension = filename.substringAfterLast('.', "").lowercase()
        return when (extension) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            "webp" -> "image/webp"
            "bmp" -> "image/bmp"
            else -> "image/jpeg"
        }
    }
}
