package io.techmeskills.an02onl_plannerapp.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.model.modules.UserModule
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch


class LoginViewModel(private val userModule: UserModule) :
    CoroutineViewModel() {

    val loggedIn: LiveData<Boolean> = userModule.checkUserLoggedIn().asLiveData()

    val errorLiveData = MutableLiveData<String>()

    fun login(firstName: String, lastName: String) {
        launch {
            try {
                if (firstName.isNotBlank() && lastName.isNotBlank()) {
                    userModule.login(firstName)
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
            userModule.logout()
        }
    }
}