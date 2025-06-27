package com.logixowl.memocam.data.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.logixowl.memocam.domain.datasource.PrefsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by AP-Jake
 * on 22/06/2025
 */

internal class MainPrefsDataSource(
    private val dataStore: DataStore<Preferences>
) : PrefsDataSource {
    override val userToken: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_USER_TOKEN].orEmpty()
        }
    override val userName: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_USER_USERNAME].orEmpty()
        }
    override val userEmail: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_USER_EMAIL].orEmpty()
        }
    override val userId: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_USER_ID].orEmpty()
        }

    override suspend fun updateUserToken(value: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_TOKEN] = value
        }
    }

    override suspend fun updateUserId(value: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = value
        }
    }

    override suspend fun updateUserEmail(value: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_EMAIL] = value
        }
    }

    override suspend fun updateUserName(value: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_USERNAME] = value
        }
    }

    companion object {
        private val KEY_USER_TOKEN = stringPreferencesKey("user_token")
        private val KEY_USER_ID = stringPreferencesKey("user_id")
        private val KEY_USER_EMAIL = stringPreferencesKey("user_email")
        private val KEY_USER_USERNAME = stringPreferencesKey("user_name")
    }
}
