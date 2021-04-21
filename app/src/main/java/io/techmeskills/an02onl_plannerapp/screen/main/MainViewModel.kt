package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.User
import io.techmeskills.an02onl_plannerapp.model.chainModules.ChainNoteModule
import io.techmeskills.an02onl_plannerapp.model.chainModules.ChainUserModule
import io.techmeskills.an02onl_plannerapp.model.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.model.preferences.SettingsStore
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(private val chainUserModule: ChainUserModule,
                    private val chainNoteModule: ChainNoteModule) :
    CoroutineViewModel() {

    val notesLiveData = chainNoteModule.currentUserNotesFlow.flowOn(Dispatchers.IO).map {
        listOf(AddNote) + it
    }.asLiveData()

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

}


object AddNote : Note(-1, "", user = -1)





