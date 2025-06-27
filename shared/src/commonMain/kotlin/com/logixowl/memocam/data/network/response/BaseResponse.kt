package com.logixowl.memocam.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by AP-Jake
 * on 27/06/2025
 */

@Serializable
internal data class BaseResponse<T>(
    val success: Boolean?,
    val message: String?,
    @SerialName("data")
    val result: T?,
)
