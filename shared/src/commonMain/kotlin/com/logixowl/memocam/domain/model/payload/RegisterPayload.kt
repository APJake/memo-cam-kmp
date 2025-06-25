package com.logixowl.memocam.domain.model.payload

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

data class RegisterPayload(
    val username: String,
    val email: String,
    val password: String,
)
