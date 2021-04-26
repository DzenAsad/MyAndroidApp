package io.techmeskills.an02onl_plannerapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.techmeskills.an02onl_plannerapp.model.dao.NotesDao
import io.techmeskills.an02onl_plannerapp.model.dao.UsersDao

@Database(
    entities = [
        Note::class,
        User::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class DB : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun usersDao(): UsersDao
}

object DatabaseConstructor {
    fun create(context: Context): DB = Room.databaseBuilder(
        context,
        DB::class.java,
        "io.techmeskills.an02onl_plannerapp.db"
    ).build()
}