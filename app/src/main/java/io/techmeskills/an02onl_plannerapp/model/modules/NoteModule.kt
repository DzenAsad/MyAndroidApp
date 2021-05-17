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
import java.text.SimpleDateFormat
import java.util.*

class NoteModule(
    private val notesDao: NotesDao,
    private val usersDao: UsersDao,
    private val settingsStore: SettingsStore,
    private val alarmModule: AlarmModule,
) {

    @ExperimentalCoroutinesApi
    val currentUserNotesFlow: Flow<List<Note>> =
        settingsStore.storedUserFlow()
            .flatMapLatest { user ->
                usersDao.getUserWithNotesFlow(user.name)
                    .map { it?.notes ?: emptyList() }
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
            val tmp = Note(
                title = note.title,
                date = note.date,
                alarmEnabled = note.alarmEnabled,
                user = settingsStore.getUser().name
            )
            val tmpId = notesDao.saveNote(tmp)
            if (note.alarmEnabled) {
                alarmModule.setAlarm(
                    Note(
                        id = tmpId,
                        title = tmp.title,
                        date = tmp.date,
                        user = tmp.user
                    )
                )
            }
        }
    }

    suspend fun saveNotes(notes: List<Note>) {
        withContext(Dispatchers.IO) {
            notesDao.saveNotes(notes)
            val updatedNotes = getCurrentUserNotes()
            updatedNotes.forEach {
                if (it.alarmEnabled) {
                    alarmModule.setAlarm(it)
                }
            }
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.getNoteById(note.id)?.let {
                alarmModule.cancelAlarm(it)
            }
            notesDao.updateNote(note)
            if (note.alarmEnabled) {
                alarmModule.setAlarm(note)
            }
        }
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.deleteNote(note)
            alarmModule.cancelAlarm(note)
        }
    }

    suspend fun postponeNote(noteId: Long) {
        withContext(Dispatchers.IO) {
            notesDao.getNoteById(noteId)?.let { note ->
                val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                val newTime = dateFormatter.parse(note.date)!!.time + 60000
                val tmp = Note(
                    id = note.id,
                    title = note.title,
                    date = dateFormatter.format(newTime),
                    user = note.user,
                    alarmEnabled = note.alarmEnabled,
                    fromCloud = note.fromCloud
                )
                updateNote(tmp)
            }
        }
    }

    suspend fun updatePos(notes: List<Note>) {
        withContext(Dispatchers.IO) {
            notes.forEachIndexed { index, note -> note.pos = index }
            notesDao.updateNotes(notes)
        }
    }

    suspend fun deleteNoteById(noteId: Long) {
        withContext(Dispatchers.IO) {

            notesDao.getNoteById(noteId)?.let {
                deleteNote(it)
                alarmModule.cancelAlarm(it)
            }
        }
    }
}