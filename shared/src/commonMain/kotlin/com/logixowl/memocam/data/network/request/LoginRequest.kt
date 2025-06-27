package com.logixowl.memocam.data.network.request

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 24/06/2025
 */

@Serializable
internal data class LoginRequest(
    val email: String,
    val password: String,
)
