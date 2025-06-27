package com.logixowl.memocam.data.repository

import com.logixowl.memocam.core.DataError
import com.logixowl.memocam.core.Result
import com.logixowl.memocam.core.onSuccess
import com.logixowl.memocam.domain.datasource.AuthNetworkDataSource
import com.logixowl.memocam.domain.model.Auth
import com.logixowl.memocam.domain.model.payload.LoginPayload
import com.logixowl.memocam.domain.model.payload.RegisterPayload
import com.logixowl.memocam.domain.repository.AuthRepository
import com.logixowl.memocam.domain.repository.PrefsRepository

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

internal class AuthRepositoryImpl(
    private val prefsRepository: PrefsRepository,
    private val authNetworkDataSource: AuthNetworkDataSource,
) : AuthRepository {
    override suspend fun login(payload: LoginPayload): Result<Auth, DataError> {
        return authNetworkDataSource.login(payload)
            .onSuccess {
                saveUser(it)
            }
    }

    override suspend fun register(payload: RegisterPayload): Result<Auth, DataError> {
        return authNetworkDataSource.register(payload)
            .onSuccess {
                saveUser(it)
            }
    }

    override suspend fun logout(): Result<Unit, DataError> {
        prefsRepository.logout()
        return Result.Success(Unit)
    }

    private suspend fun saveUser(auth: Auth) {
        prefsRepository.updateUser(
            user = auth.user,
            token = auth.token
        )
    }
}
