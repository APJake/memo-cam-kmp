package com.logixowl.memocam.domain.datasource

import com.logixowl.memocam.core.DataError
import com.logixowl.memocam.core.Result
import com.logixowl.memocam.domain.model.Auth
import com.logixowl.memocam.domain.model.payload.LoginPayload
import com.logixowl.memocam.domain.model.payload.RegisterPayload

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

interface AuthNetworkDataSource {

    suspend fun login(payload: LoginPayload): Result<Auth, DataError.Remote>

    suspend fun register(payload: RegisterPayload): Result<Auth, DataError.Remote>

}
