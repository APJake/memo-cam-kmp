package com.logixowl.memocam.request

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class FolderRequest(
    val name: String,
    val description: String? = null,
    val posterImage: String? = null,
    val iconId: Int = 1,
)
