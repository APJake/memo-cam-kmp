package com.logixowl.memocam.response

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class UserResponse(
    val id: String,
    val username: String,
    val email: String
)
