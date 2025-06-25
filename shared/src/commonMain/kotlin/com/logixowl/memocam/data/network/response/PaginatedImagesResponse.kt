package com.logixowl.memocam.data.network.response

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

data class PaginatedImagesResponse(
    val images: List<FolderImageResponse>?,
    val currentPage: Int?,
    val totalPages: Int?,
    val totalCount: Long?,
    val hasNext: Boolean?,
    val hasPrevious: Boolean?,
)
