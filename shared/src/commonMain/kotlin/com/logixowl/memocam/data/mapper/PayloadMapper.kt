package com.logixowl.memocam.data.mapper

import com.logixowl.memocam.data.network.request.CreateFolderRequest
import com.logixowl.memocam.data.network.request.LoginRequest
import com.logixowl.memocam.data.network.request.RegisterRequest
import com.logixowl.memocam.data.network.request.UpdateFolderRequest
import com.logixowl.memocam.domain.model.payload.CreateFolderPayload
import com.logixowl.memocam.domain.model.payload.LoginPayload
import com.logixowl.memocam.domain.model.payload.RegisterPayload
import com.logixowl.memocam.domain.model.payload.UpdateFolderPayload

/**
 * Created by AP-Jake
 * on 24/06/2025
 */

internal object PayloadMapper {

    fun mapToRegisterRequest(payload: RegisterPayload) = RegisterRequest(
        username = payload.username,
        email = payload.email,
        password = payload.password,
    )

    fun mapToLoginRequest(payload: LoginPayload) = LoginRequest(
        email = payload.email,
        password = payload.password,
    )

    fun mapToCreateFolderRequest(payload: CreateFolderPayload) = with(payload) {
        CreateFolderRequest(
            name = name,
            description = description,
            iconId = iconId,
        )
    }

    fun mapToUpdateFolderRequest(payload: UpdateFolderPayload) = with(payload) {
        UpdateFolderRequest(
            name = name,
            description = description,
            iconId = iconId,
            posterImage = posterImage
        )
    }

}
