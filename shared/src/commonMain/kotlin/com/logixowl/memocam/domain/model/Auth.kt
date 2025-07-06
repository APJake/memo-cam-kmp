package com.logixowl.memocam.domain.model

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

data class Auth(
    val token: String,
    val user: User,
)
