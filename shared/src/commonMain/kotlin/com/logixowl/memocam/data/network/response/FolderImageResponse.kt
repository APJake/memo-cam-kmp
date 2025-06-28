package com.logixowl.memocam.data.network.response

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

@Serializable
internal data class FolderImageResponse(
    val id: String? = null,
    val fileName: String? = null,
    val originalName: String? = null,
    val size: Long? = null,
    val isFrontCam: Boolean? = null,
    val contentType: String? = null,
    val uploadedAt: Long? = null,
)
