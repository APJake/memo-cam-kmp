package com.logixowl.memocam.request

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 21/06/2025
 */

@Serializable
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)
