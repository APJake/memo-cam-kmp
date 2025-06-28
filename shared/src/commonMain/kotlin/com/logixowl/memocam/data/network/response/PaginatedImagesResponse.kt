package com.logixowl.memocam.data.network.response

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

@Serializable
internal data class PaginatedImagesResponse(
    val images: List<FolderImageResponse>? = null,
    val currentPage: Int? = null,
    val totalPages: Int? = null,
    val totalCount: Long? = null,
    val hasNext: Boolean? = null,
    val hasPrevious: Boolean? = null,
)
