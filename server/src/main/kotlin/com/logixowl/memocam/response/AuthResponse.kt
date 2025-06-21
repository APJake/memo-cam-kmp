package com.logixowl.memocam.response

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class AuthResponse(
    val token: String,
    val user: UserResponse
)
