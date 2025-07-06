package com.logixowl.memocam.data.network.datasource

import com.logixowl.memocam.core.DataError
import com.logixowl.memocam.core.Result
import com.logixowl.memocam.core.map
import com.logixowl.memocam.data.mapper.PayloadMapper
import com.logixowl.memocam.data.mapper.ResponseMapper
import com.logixowl.memocam.data.network.response.AuthResponse
import com.logixowl.memocam.data.network.util.AppUrl
import com.logixowl.memocam.data.network.util.safeCall
import com.logixowl.memocam.domain.datasource.AuthNetworkDataSource
import com.logixowl.memocam.domain.model.Auth
import com.logixowl.memocam.domain.model.payload.LoginPayload
import com.logixowl.memocam.domain.model.payload.RegisterPayload
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

/**
 * Created by AP-Jake
 * on 24/06/2025
 */


internal class AuthNetworkDataSourceImpl(
    private val client: HttpClient,
) : AuthNetworkDataSource {
    override suspend fun login(payload: LoginPayload): Result<Auth, DataError.Remote> {
        return safeCall<AuthResponse> {
            client.post(AppUrl.login) {
                setBody(PayloadMapper.mapToLoginRequest(payload))
            }
        }.map(ResponseMapper::mapToAuthDomain)
    }

    override suspend fun register(payload: RegisterPayload): Result<Auth, DataError.Remote> {
        return safeCall<AuthResponse> {
            client.post(AppUrl.register) {
                setBody(PayloadMapper.mapToRegisterRequest(payload))
            }
        }.map(ResponseMapper::mapToAuthDomain)
    }
}
