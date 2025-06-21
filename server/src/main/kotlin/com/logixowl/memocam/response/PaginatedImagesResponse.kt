package com.logixowl.memocam.response

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class PaginatedImagesResponse(
    val images: List<ImageResponse>,
    val currentPage: Int,
    val totalPages: Int,
    val totalCount: Long,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)
