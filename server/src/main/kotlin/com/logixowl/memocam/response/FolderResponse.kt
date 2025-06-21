package com.logixowl.memocam.response

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class FolderResponse(
    val id: String,
    val name: String,
    val createdAt: Long,
    val imageCount: Int = 0
)
