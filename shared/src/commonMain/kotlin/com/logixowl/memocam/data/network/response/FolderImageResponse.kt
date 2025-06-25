package com.logixowl.memocam.data.network.response

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

data class FolderImageResponse(
    val id: String?,
    val fileName: String?,
    val originalName: String?,
    val size: Long?,
    val contentType: String?,
    val uploadedAt: Long?,
)
