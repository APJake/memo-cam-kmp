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

    override suspend fun updateUserToken(value: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_TOKEN] = value
        }
    }

    companion object {
        private val KEY_USER_TOKEN = stringPreferencesKey("user_token")
    }
}
