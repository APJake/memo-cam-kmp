package com.logixowl.memocam.base

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)
