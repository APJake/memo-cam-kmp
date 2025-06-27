package com.logixowl.memocam.data.network.request

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

@Serializable
internal data class UpdateFolderRequest(
    val name: String,
    val description: String,
    val iconId: Int,
    val posterImage: String?,
)
