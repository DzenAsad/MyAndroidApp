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

class ChainUserModule(context: Context,
                      private val usersDao: UsersDao,
                      private val settingsStore: SettingsStore) {

    val allUserNames = usersDao.getAllUsers()

    suspend fun login(firsName: String, lastName: String) {
        withContext(Dispatchers.IO) { //указываем, что метод должен выполниться в IO
            val userId: Long = if (checkUserExists(firsName, lastName).not()) { //проверяем существует ли в базе юзер с таким именем
                usersDao.saveUser(User(firstName = firsName, lastName = lastName)) //добавляем в базу нового юзера, берем его сгенерированный базой id
            } else {
                usersDao.getUserId(firsName, lastName)

            }
            settingsStore.setUser(User(userId, firsName, lastName))
        }
    }

    private suspend fun checkUserExists(firsName: String, lastName: String): Boolean {
        return withContext(Dispatchers.IO) {
            usersDao.getUserId(firsName, lastName) > 0
        }
    }

    fun checkUserLoggedIn(): Flow<Boolean> =
        settingsStore.storedUserFlow().map { it.userId >= 0 }.flowOn(Dispatchers.IO)

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            settingsStore.setUser(User(-1, "", ""))
        }
    }

    fun getCurrentUser(): Flow<User>{
        return settingsStore.storedUserFlow()
    }

    @ExperimentalCoroutinesApi
    fun getCurrentUserFlow(): Flow<User> = settingsStore.storedUserFlow().flatMapLatest {
        usersDao.getById(it.userId)
    }

    @SuppressLint("HardwareIds")
    val phoneId: String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}