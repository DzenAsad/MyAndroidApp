package io.techmeskills.an02onl_plannerapp.screen.add

import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import io.techmeskills.an02onl_plannerapp.model.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.User
import io.techmeskills.an02onl_plannerapp.model.chainModules.ChainNoteModule
import io.techmeskills.an02onl_plannerapp.model.preferences.SettingsStore
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddViewModel(private val chainNoteModule: ChainNoteModule) : CoroutineViewModel() {

    fun addNewNote(note: Note) {
        launch {
            chainNoteModule.saveNote(note)
        }
    }

    fun updateNote(note: Note) {
        launch {
            chainNoteModule.updateNote(note)
        }
    }

}