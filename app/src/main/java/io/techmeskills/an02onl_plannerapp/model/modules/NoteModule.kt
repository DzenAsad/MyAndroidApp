package io.techmeskills.an02onl_plannerapp.model.modules

import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.model.dao.UsersDao
import io.techmeskills.an02onl_plannerapp.model.preferences.SettingsStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NoteModule(
    private val notesDao: NotesDao,
    private val usersDao: UsersDao,
    private val settingsStore: SettingsStore,
) {

    @ExperimentalCoroutinesApi
    val currentUserNotesFlow: Flow<List<Note>> =
        settingsStore.storedUserFlow()
            .flatMapLatest { user -> //получаем из сеттингов текущий айди юзера
                usersDao.getUserWithNotesFlow(user.name)
                    .map { it?.notes ?: emptyList() } //получаем заметки по айди юзера
            }

    suspend fun getCurrentUserNotes(): List<Note> {
        return usersDao.getUserWithNotes(settingsStore.getUser().name)?.notes ?: emptyList()
    }

    suspend fun setAllNotesSyncWithCloud() {
        withContext(Dispatchers.IO) {
            notesDao.setAllNotesSyncWithCloud()
        }
    }

    suspend fun saveNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.saveNote(
                Note(
                    title = note.title,
                    date = note.date,
                    user = settingsStore.getUser().name
                )
            )
        }
    }

    suspend fun saveNotes(notes: List<Note>) {
        withContext(Dispatchers.IO) {
            notesDao.saveNotes(notes)

        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.updateNote(note)
        }
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.deleteNote(note)
        }
    }

    suspend fun updatePos(notes: List<Note>) {
        withContext(Dispatchers.IO) {
            notes.forEachIndexed { index, note -> note.pos = index }
            notesDao.updateNotes(notes)
        }
    }
}