package io.techmeskills.an02onl_plannerapp.model.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.techmeskills.an02onl_plannerapp.BuildConfig
import io.techmeskills.an02onl_plannerapp.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    "${BuildConfig.BUILD_TYPE}_datastore"
)

class SettingsStore(context: Context) {

    private val dataStore = context.dataStore

    fun storedUserFlow(): Flow<User> = dataStore.data.map { preferences ->
        User(
            preferences[longPreferencesKey(USER_ID)] ?: -1,
            preferences[stringPreferencesKey(FIRST_NAME)] ?: "",
            preferences[stringPreferencesKey(LAST_NAME)] ?: ""
        )
    }

    suspend fun getUser(): User = storedUserFlow().first()

    suspend fun setUser(user: User) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(FIRST_NAME)] = user.firstName
            preferences[stringPreferencesKey(LAST_NAME)] = user.lastName
            preferences[longPreferencesKey(USER_ID)] = user.userId
        }
    }


    suspend fun clearSavedUser() {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(FIRST_NAME)] = ""
            preferences[stringPreferencesKey(LAST_NAME)] = ""
            preferences[longPreferencesKey(USER_ID)] = -1
        }
    }

    companion object {

        private const val FIRST_NAME = "firstName"
        private const val LAST_NAME = "lastName"
        private const val USER_ID = "userId"

    }
}