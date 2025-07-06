package com.logixowl.memocam.data.repository

import com.logixowl.memocam.core.DataError
import com.logixowl.memocam.core.Result
import com.logixowl.memocam.core.onSuccess
import com.logixowl.memocam.domain.datasource.AuthNetworkDataSource
import com.logixowl.memocam.domain.datasource.PrefsDataSource
import com.logixowl.memocam.domain.model.Auth
import com.logixowl.memocam.domain.model.payload.LoginPayload
import com.logixowl.memocam.domain.model.payload.RegisterPayload
import com.logixowl.memocam.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

internal class AuthRepositoryImpl(
    private val prefsDataSource: PrefsDataSource,
    private val authNetworkDataSource: AuthNetworkDataSource,
) : AuthRepository {

    override val isLoggedIn: Flow<Boolean>
        get() = prefsDataSource.userToken.mapNotNull { it.isNotBlank() }

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
        prefsDataSource.updateUserId("")
        prefsDataSource.updateUserName("")
        prefsDataSource.updateUserToken("")
        return Result.Success(Unit)
    }

    private suspend fun saveUser(auth: Auth) {
        prefsDataSource.updateUserId(auth.user.id)
        prefsDataSource.updateUserEmail(auth.user.email)
        prefsDataSource.updateUserName(auth.user.username)
        prefsDataSource.updateUserToken(auth.token)
    }
}
