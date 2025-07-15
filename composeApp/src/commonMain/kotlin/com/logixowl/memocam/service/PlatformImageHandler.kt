package com.logixowl.memocam.service

import com.logixowl.memocam.model.ImageData

/**
 * Created by AP-Jake
 * on 15/07/2025
 */

expect class PlatformImageHandler {
    suspend fun getImageData(uri: String): ImageData?
}
