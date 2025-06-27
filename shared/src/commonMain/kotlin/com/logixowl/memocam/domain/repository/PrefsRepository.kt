package com.logixowl.memocam.domain.repository

import com.logixowl.memocam.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by AP-Jake
 * on 27/06/2025
 */

interface PrefsRepository {

    val isLoggedIn: Flow<Boolean>
    val user: Flow<User>

    suspend fun updateUser(user: User, token: String)
    suspend fun logout()

}
