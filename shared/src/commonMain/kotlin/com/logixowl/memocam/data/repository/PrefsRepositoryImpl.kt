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

class PrefsRepositoryImpl(
    private val prefsDataSource: PrefsDataSource,
) : PrefsRepository {

    override val isLoggedIn: Flow<Boolean>
        get() = prefsDataSource.userToken.mapNotNull { it.isNotBlank() }

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

    override suspend fun updateUser(user: User, token: String) {
        prefsDataSource.updateUserId(user.id)
        prefsDataSource.updateUserEmail(user.email)
        prefsDataSource.updateUserName(user.username)
        prefsDataSource.updateUserToken(token)
    }

    override suspend fun logout() {
        prefsDataSource.updateUserId("")
        prefsDataSource.updateUserEmail("")
        prefsDataSource.updateUserName("")
        prefsDataSource.updateUserToken("")
    }
}
