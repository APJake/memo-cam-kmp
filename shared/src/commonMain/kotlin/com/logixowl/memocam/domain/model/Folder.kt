package com.logixowl.memocam.domain.model

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

data class Folder(
    val id: String,
    val name: String,
    val createdAt: Long,
    val posterImage: String?,
    val imageCount: Int,
)
