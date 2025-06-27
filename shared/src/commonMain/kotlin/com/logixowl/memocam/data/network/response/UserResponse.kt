package com.logixowl.memocam.data.network.response

import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

@Serializable
internal data class UserResponse(
    val id: String?,
    val username: String?,
    val email: String?,
)
