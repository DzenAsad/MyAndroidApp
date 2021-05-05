package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.modules.CloudModule
import io.techmeskills.an02onl_plannerapp.model.modules.NoteModule
import io.techmeskills.an02onl_plannerapp.model.modules.UserModule
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val userModule: UserModule,
    private val noteModule: NoteModule,
    private val cloudModule: CloudModule,
) : CoroutineViewModel() {

    val notesLiveData = noteModule.currentUserNotesFlow.flowOn(Dispatchers.IO).map {
        listOf(AddNote) + it
    }.asLiveData()

    val progressLiveData = MutableLiveData<Boolean>()

    val progressEditUser = MutableLiveData<Boolean>()

    val currentUser = userModule.getCurrentUser().flowOn(Dispatchers.IO).asLiveData()


    fun deleteNote(note: Note) {
        launch {
            noteModule.deleteNote(note)
        }
    }

    fun logout(): Job {
        return launch {
            userModule.logout()
        }
    }

    fun updatePos(notes: List<Note>) {
        launch {
            noteModule.updatePos(notes)
        }
    }

    fun delCurrUser() {
        launch {
            userModule.delCurrUser()
            userModule.logout()
        }
    }

    fun updtCurrUserAsync(newName: String) = launch {
        val result = userModule.updtCurrUser(newName)
        progressEditUser.postValue(result)

    }

    fun exportNotes() = launch {
        val result = cloudModule.exportNotes()
        progressLiveData.postValue(result)
    }

    fun importNotes() = launch {
        val result = cloudModule.importNotes()
        progressLiveData.postValue(result)
    }

}


object AddNote : Note(0, "", " ", user = "", pos = -1)





