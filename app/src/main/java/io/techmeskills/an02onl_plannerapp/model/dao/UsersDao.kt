package io.techmeskills.an02onl_plannerapp.model.dao

import androidx.room.*
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.User
import io.techmeskills.an02onl_plannerapp.model.UserWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UsersDao {

    @Update
    abstract fun updateUser(user: User)

    @Delete
    abstract fun deleteUser(user: User)

    @Query("SELECT id FROM users WHERE name == :name")
    abstract fun getUserId(name: String): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveUser(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveUser(users: List<User>): List<Long>

    @Query("SELECT * FROM users")
    abstract fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE name == :name")
    abstract fun getByName(name: String): Flow<User>

    @Transaction
    @Query("SELECT * FROM users WHERE name == :name LIMIT 1")
    abstract fun getUserWithNotesFlow(name: String): Flow<UserWithNotes?>

    @Transaction
    @Query("SELECT * FROM users WHERE name == :name LIMIT 1")
    abstract fun getUserWithNotes(name: String): UserWithNotes?
}