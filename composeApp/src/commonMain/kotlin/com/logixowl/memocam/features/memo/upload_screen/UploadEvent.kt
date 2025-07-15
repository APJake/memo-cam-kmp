package com.logixowl.memocam.features.memo.upload_screen

/**
 * Created by AP-Jake
 * on 15/07/2025
 */

sealed interface UploadEvent {
    data class OnSuccess(
        val imageId: String,
    ) : UploadEvent
}
