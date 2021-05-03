package io.techmeskills.an02onl_plannerapp.model.chainModules

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import io.techmeskills.an02onl_plannerapp.model.User
import io.techmeskills.an02onl_plannerapp.model.dao.UsersDao
import io.techmeskills.an02onl_plannerapp.model.preferences.SettingsStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ChainUserModule(
    context: Context,
    private val usersDao: UsersDao,
    private val settingsStore: SettingsStore,
) {

    val allUserNames = usersDao.getAllUsers()

    suspend fun login(name: String) {
        withContext(Dispatchers.IO) {
            val userId: Long = if (checkUserExists(name).not()) {
                usersDao.saveUser(User(name = name))
            } else {
                usersDao.getUserId(name)

            }
            settingsStore.setUser(User(userId, name))
        }
    }

    private suspend fun checkUserExists(name: String): Boolean {
        return withContext(Dispatchers.IO) {
            usersDao.getUserId(name) > 0
        }
    }

    fun checkUserLoggedIn(): Flow<Boolean> =
        settingsStore.storedUserFlow().map { it.name.isNotEmpty() }.flowOn(Dispatchers.IO)

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            settingsStore.setUser(User(name = ""))
        }
    }

    fun getCurrentUser(): Flow<User> {
        return settingsStore.storedUserFlow()
    }

    suspend fun updtCurrUser(newName: String): Boolean {
        return if (checkUserExists(newName).not()) {
            val curUser = settingsStore.getUser()
            val newUser = User(curUser.id, newName, curUser.passwd)
            usersDao.updateUser(newUser)
            settingsStore.setUser(newUser)
            true
        } else false
    }

    suspend fun delCurrUser() {
        withContext(Dispatchers.IO) {
            usersDao.deleteUser(settingsStore.getUser())
        }

    }

    @ExperimentalCoroutinesApi
    fun getCurrentUserFlow(): Flow<User> = settingsStore.storedUserFlow().flatMapLatest {
        usersDao.getByName(it.name)
    }

    @SuppressLint("HardwareIds")
    val phoneId: String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}