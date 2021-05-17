package io.techmeskills.an02onl_plannerapp.screen.add

import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.modules.NoteModule
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class AddViewModel(
    private val noteModule: NoteModule

    ) : CoroutineViewModel() {

    fun addNewNote(note: Note) {
        launch {
            noteModule.saveNote(note)
        }
    }

    fun updateNote(note: Note) {
        launch {
            noteModule.updateNote(note)
        }
    }

}