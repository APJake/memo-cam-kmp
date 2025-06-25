package com.logixowl.memocam.domain.datasource

import kotlinx.coroutines.flow.Flow

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

interface PrefsDataSource {

    val userToken: Flow<String>

    suspend fun updateUserToken(value: String)

}
