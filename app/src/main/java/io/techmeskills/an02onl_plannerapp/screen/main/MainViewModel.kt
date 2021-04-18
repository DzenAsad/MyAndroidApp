package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.asLiveData
import io.techmeskills.an02onl_plannerapp.Note
import io.techmeskills.an02onl_plannerapp.User
import io.techmeskills.an02onl_plannerapp.model.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.model.sharedPrefs.SharPrefUser
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(private val sharPrefUser: SharPrefUser, private val notesDao: NotesDao) :
    CoroutineViewModel() {


    val notesLiveData =
        notesDao.getAllUserNotes(getSavedUser()!!.userId).map { (listOf(AddNote) + it).sortedBy { it.position } }
            .flowOn(Dispatchers.IO).asLiveData()


    fun deleteNote(note: Note) {
        launch {
            notesDao.deleteNote(note)
        }
    }


    fun updateNotes(notes: List<Note>, fromPos: Int, toPos: Int) {
        launch {
            val tmp = notes.mapIndexed{index, note -> note.apply {if (fromPos == index) note.position = toPos.toLong() }}
            notesDao.updateNotes(tmp)
        }
    }

    fun getSavedUser(): User? {
        if (sharPrefUser.getSavedUser() != null) {


            return sharPrefUser.getSavedUser()
        }
        return null
    }

    fun clearSavedUser() {
        sharPrefUser.clearSavedUser()
    }

}


object AddNote : Note(-1, "", user = -1)





