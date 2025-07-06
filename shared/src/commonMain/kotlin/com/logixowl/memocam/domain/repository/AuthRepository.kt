package com.logixowl.memocam.domain.repository

import com.logixowl.memocam.core.DataError
import com.logixowl.memocam.core.Result
import com.logixowl.memocam.domain.model.Auth
import com.logixowl.memocam.domain.model.payload.LoginPayload
import com.logixowl.memocam.domain.model.payload.RegisterPayload
import kotlinx.coroutines.flow.Flow

/**
 * Created by AP-Jake
 * on 25/06/2025
 */

interface AuthRepository {

    val isLoggedIn: Flow<Boolean>

    suspend fun login(payload: LoginPayload): Result<Auth, DataError>

    suspend fun register(payload: RegisterPayload): Result<Auth, DataError>

    suspend fun logout(): Result<Unit, DataError>

}
