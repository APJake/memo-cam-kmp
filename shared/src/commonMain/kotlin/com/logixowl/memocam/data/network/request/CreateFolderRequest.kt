package com.logixowl.memocam.data.network.request

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

@Serializable
data class CreateFolderRequest(
    val name: String,
)
