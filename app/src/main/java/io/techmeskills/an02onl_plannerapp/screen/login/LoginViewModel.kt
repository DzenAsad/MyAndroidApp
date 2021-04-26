package io.techmeskills.an02onl_plannerapp.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.model.User
import io.techmeskills.an02onl_plannerapp.model.chainModules.ChainNoteModule
import io.techmeskills.an02onl_plannerapp.model.chainModules.ChainUserModule
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class LoginViewModel(private val chainUserModule: ChainUserModule) :
    CoroutineViewModel() {

    val loggedIn: LiveData<Boolean> = chainUserModule.checkUserLoggedIn().asLiveData()

    val errorLiveData = MutableLiveData<String>()

    fun login(firstName: String, lastName: String) {
        launch {
            try {
                if (firstName.isNotBlank() && lastName.isNotBlank()) {
                    chainUserModule.login(firstName, lastName)
                } else {
                    errorLiveData.postValue("Enter user name")
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e.message)
            }
        }
    }

    fun clear() {
        launch {
            chainUserModule.logout()
        }
    }
}