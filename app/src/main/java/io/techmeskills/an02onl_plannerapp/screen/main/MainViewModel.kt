package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.chainModules.ChainCloudModule
import io.techmeskills.an02onl_plannerapp.model.chainModules.ChainNoteModule
import io.techmeskills.an02onl_plannerapp.model.chainModules.ChainUserModule
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val chainUserModule: ChainUserModule,
    private val chainNoteModule: ChainNoteModule,
    private val chainCloudModule: ChainCloudModule,
) : CoroutineViewModel() {

    val notesLiveData = chainNoteModule.currentUserNotesFlow.flowOn(Dispatchers.IO).map {
        listOf(AddNote) + it.sortedBy { it.pos }
    }.asLiveData()

    val progressLiveData = MutableLiveData<Boolean>()

    val progressEditUser = MutableLiveData<Boolean>()

    val currentUser = chainUserModule.getCurrentUser().flowOn(Dispatchers.IO).asLiveData()


    fun deleteNote(note: Note) {
        launch {
            chainNoteModule.deleteNote(note)
        }
    }

    fun logout(): Job {
        return launch {
            chainUserModule.logout()
        }
    }

    fun updatePos(notes: List<Note>) {
        launch {
            chainNoteModule.updatePos(notes)
        }
    }

    fun delCurrUser() {
        launch {
            chainUserModule.delCurrUser()
            chainUserModule.logout()
        }
    }

    fun updtCurrUserAsync(newName: String) = launch {
        val result = chainUserModule.updtCurrUser(newName)
        progressEditUser.postValue(result)

    }

    fun exportNotes() = launch {
        val result = chainCloudModule.exportNotes()
        progressLiveData.postValue(result)
    }

    fun importNotes() = launch {
        val result = chainCloudModule.importNotes()
        progressLiveData.postValue(result)
    }

}


object AddNote : Note(-1, "", " ", user = "", pos = -1)





