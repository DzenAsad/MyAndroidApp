package io.techmeskills.an02onl_plannerapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.User
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UsersDao {
    @Query("SELECT user_id FROM users WHERE first_name == :firstName and last_name == :lastName")
    abstract fun getUserId(firstName: String, lastName: String): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveUser(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveUser(users: List<User>): List<Long>

    @Query("SELECT * FROM users")
    abstract fun getAllUsers(): Flow<List<User>>


}