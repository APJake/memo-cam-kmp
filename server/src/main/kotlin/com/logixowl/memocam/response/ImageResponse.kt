package com.logixowl.memocam.response

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class ImageResponse(
    val id: String,
    val fileName: String,
    val originalName: String,
    val size: Long,
    val contentType: String,
    val uploadedAt: Long
)
