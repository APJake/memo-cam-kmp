package com.logixowl.memocam.base

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class ErrorResponse(
    val success: Boolean = false,
    val message: String,
    val code: String? = null
)
