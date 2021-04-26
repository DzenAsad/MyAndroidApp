package io.techmeskills.an02onl_plannerapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "users", indices = [Index(value = ["first_name", "last_name"], unique = true)])
class User(
    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = true) val userId: Long = 0L,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String
) : Parcelable