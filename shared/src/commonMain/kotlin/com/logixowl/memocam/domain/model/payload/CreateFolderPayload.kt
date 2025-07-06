package com.logixowl.memocam.domain.model.payload

/**
 * Created by AP-Jake
 * on 27/06/2025
 */

data class CreateFolderPayload(
    val name: String,
    val description: String,
    val iconId: Int,
)
