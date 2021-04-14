package io.techmeskills.an02onl_plannerapp.screen.add

import io.techmeskills.an02onl_plannerapp.model.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.screen.main.Note
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class AddViewModel(private val notesDao: NotesDao) : CoroutineViewModel() {
    fun addNewNote(note: Note) {
        launch {
            notesDao.saveNote(note)
        }
    }

    fun updateNote(note: Note) {
        launch {
            notesDao.updateNote(note)
        }
    }
}