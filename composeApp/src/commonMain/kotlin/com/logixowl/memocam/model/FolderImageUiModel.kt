package com.logixowl.memocam.model

/**
 * Created by AP-Jake
 * on 05/07/2025
 */

data class FolderImageUiModel(
    val id: String,
    val url: String,
    val fileName: String,
    val originalName: String,
    val size: Long,
    val isFrontCam: Boolean,
    val contentType: String,
    val uploadedAt: Long,
    val dayNumber: String,
) {
    companion object
}
