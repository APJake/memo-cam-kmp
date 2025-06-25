package com.logixowl.memocam.data.mapper

import com.logixowl.memocam.data.network.request.LoginRequest
import com.logixowl.memocam.data.network.request.RegisterRequest
import com.logixowl.memocam.domain.model.payload.LoginPayload
import com.logixowl.memocam.domain.model.payload.RegisterPayload

/**
 * Created by AP-Jake
 * on 24/06/2025
 */

internal object PayloadMapper {

    fun mapToRequest(payload: RegisterPayload) = RegisterRequest(
        username = payload.username,
        email = payload.email,
        password = payload.password,
    )

    fun mapToRequest(payload: LoginPayload) = LoginRequest(
        username = payload.username,
        password = payload.password,
    )

}
