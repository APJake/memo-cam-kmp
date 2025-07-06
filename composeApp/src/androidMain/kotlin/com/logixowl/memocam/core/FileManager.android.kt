package com.logixowl.memocam.core

import java.io.File

/**
 * Created by AP-Jake
 * on 06/07/2025
 */

actual object FileManager {
    actual fun deleteFile(filePath: String): Boolean {
        return try {
            val file = File(filePath)
            if (!file.exists()) return true

            file.delete().also {
                if (it) {
                    AppLogger.d("FileManager.android", "Deleted file: $filePath")
                } else {
                    AppLogger.e("FileManager.android", "Failed to delete file: $filePath")
                }
            }
        } catch (e: Exception) {
            AppLogger.e("CameraPreviewNative.android", "Failed to save image: ${e.message}")
            false
        }
    }
}
