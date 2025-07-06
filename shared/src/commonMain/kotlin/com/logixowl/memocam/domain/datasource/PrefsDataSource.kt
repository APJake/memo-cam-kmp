package com.logixowl.memocam.domain.datasource

import kotlinx.coroutines.flow.Flow

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

interface PrefsDataSource {

    val userToken: Flow<String>
    val userId: Flow<String>
    val userEmail: Flow<String>
    val userName: Flow<String>

    suspend fun updateUserToken(value: String)
    suspend fun updateUserId(value: String)
    suspend fun updateUserEmail(value: String)
    suspend fun updateUserName(value: String)
}
