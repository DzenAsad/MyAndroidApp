package io.techmeskills.an02onl_plannerapp.model.dao

import androidx.room.*
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.UserWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveNotes(notes: List<Note>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateNotes(notes: List<Note>)

    @Delete
    abstract fun deleteNote(note: Note)

    @Delete
    abstract fun deleteNotes(notes: List<Note>)

    @Transaction
    @Query("SELECT * FROM notes, users")
    abstract fun getAllUserWithNotes(): Flow<List<UserWithNotes>>

    @Transaction
    @Query("SELECT * FROM notes WHERE user == :id")
    abstract fun getAllUserNotes(id: Long): Flow<List<Note>>

    @Transaction
    @Query("SELECT * FROM notes WHERE user == :id")
    abstract fun getAllUserNotesList(id: Long): List<Note>

    @Query("UPDATE notes SET fromCloud = 1")
    abstract fun setAllNotesSyncWithCloud()

}