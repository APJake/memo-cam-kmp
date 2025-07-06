package com.logixowl.memocam.data.network.response

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

@Serializable
internal data class FolderResponse(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val iconId: Int? = null,
    val createdAt: Long? = null,
    val posterImage: String? = null,
    val imageCount: Int? = null,
)
