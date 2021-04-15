package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import io.techmeskills.an02onl_plannerapp.model.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val notesDao: NotesDao) : CoroutineViewModel() {


    val notesLiveData = MutableLiveData<List<Note>>(listOf(AddNote))


    fun deleteNote(note: Note) {
        launch {
            notesDao.deleteNote(note)
        }
        invalidateList()
    }

    fun invalidateList() {
        launch {
            val notes = notesDao.getAllNotes()
            notesLiveData.postValue(listOf(AddNote) + notes)
        }
    }

    fun updateTwoNote(note1: Note, note2: Note) {
        launch {
            notesDao.updateNote(note1)
            notesDao.updateNote(note2)
        }
    }

}


object AddNote : Note(-1, "", "")





