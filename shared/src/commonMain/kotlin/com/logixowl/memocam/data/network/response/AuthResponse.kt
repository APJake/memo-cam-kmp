package com.logixowl.memocam.data.network.response

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

data class AuthResponse(
    val token: String?,
    val user: UserResponse?,
)
