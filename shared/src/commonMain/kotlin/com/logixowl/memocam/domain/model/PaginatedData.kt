package com.logixowl.memocam.domain.model

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

data class PaginatedData<T>(
    val images: List<T>,
    val currentPage: Int,
    val totalPages: Int,
    val totalCount: Long,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)
