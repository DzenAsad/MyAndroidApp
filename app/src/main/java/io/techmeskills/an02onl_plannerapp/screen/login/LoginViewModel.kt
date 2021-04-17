package io.techmeskills.an02onl_plannerapp.screen.login

import io.techmeskills.an02onl_plannerapp.User
import io.techmeskills.an02onl_plannerapp.model.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.model.sharedPrefs.SharPrefUser
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async


class LoginViewModel(private val sharPrefUser: SharPrefUser, private val notesDao: NotesDao) :
    CoroutineViewModel() {

    fun getUser(): User? {
        return sharPrefUser.getSavedUser()
    }

    fun saveUser(user: User): Deferred<Unit> {
        val a = async {
            val idUser = notesDao.getUserId(user.firstName, user.lastName)
            if (idUser != 0L) {
                sharPrefUser.setSavedUser(user) { idUser }
            } else {
                sharPrefUser.setSavedUser(user) { notesDao.saveUser(it) }
            }
        }
        return a
    }
}