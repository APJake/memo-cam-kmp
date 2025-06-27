package com.logixowl.memocam.data.repository

import com.logixowl.memocam.domain.datasource.PrefsDataSource
import com.logixowl.memocam.domain.model.User
import com.logixowl.memocam.domain.repository.PrefsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapNotNull

/**
 * Created by AP-Jake
 * on 27/06/2025
 */

internal class PrefsRepositoryImpl(
    private val prefsDataSource: PrefsDataSource,
) : PrefsRepository {

    override val user: Flow<User>
        get() = combine(
            prefsDataSource.userId,
            prefsDataSource.userEmail,
            prefsDataSource.userName
        ) { id, email, name ->
            User(
                id = id,
                email = email,
                username = name,
            )
        }
}
