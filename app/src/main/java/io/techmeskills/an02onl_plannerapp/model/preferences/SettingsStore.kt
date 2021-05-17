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
            preferences[longPreferencesKey(ID)] ?: 0,
            preferences[stringPreferencesKey(NAME)] ?: "",
            preferences[stringPreferencesKey(PASSWD)] ?: ""
        )
    }

    suspend fun getUser(): User = storedUserFlow().first()

    suspend fun setUser(user: User) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(ID)] = user.id
            preferences[stringPreferencesKey(NAME)] = user.name
            preferences[stringPreferencesKey(PASSWD)] = user.passwd
        }
    }

    companion object {
        private const val ID = "id"
        private const val NAME = "name"
        private const val PASSWD = "passwd"

    }
}