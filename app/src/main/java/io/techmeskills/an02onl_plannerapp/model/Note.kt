package io.techmeskills.an02onl_plannerapp.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
open class Note(
        @PrimaryKey(autoGenerate = true) val id: Long = 0L,
        val title: String,
        val date: String,
        @ColumnInfo(index = true, name = "user")
        val user: String,
        var pos: Int = 0,
        val fromCloud: Boolean = false
) : Parcelable {
        override fun equals(other: Any?): Boolean {
                if (other is Note) {return title == other.title && date == other.date && user == other.user}
                return super.equals(other)
        }

        override fun hashCode(): Int {
                var result = id.hashCode()
                result = 31 * result + title.hashCode()
                result = 31 * result + date.hashCode()
                result = 31 * result + user.hashCode()
                result = 31 * result + fromCloud.hashCode()
                return result
        }
}