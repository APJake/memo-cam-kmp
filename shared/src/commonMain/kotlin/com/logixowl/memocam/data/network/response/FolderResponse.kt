package com.logixowl.memocam.data.network.response

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

@Serializable
internal data class FolderResponse(
    val id: String?,
    val name: String?,
    val description: String?,
    val iconId: Int?,
    val createdAt: Long?,
    val posterImage: String?,
    val imageCount: Int?,
)
