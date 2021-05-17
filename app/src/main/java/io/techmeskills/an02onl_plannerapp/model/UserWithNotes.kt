package io.techmeskills.an02onl_plannerapp.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.User
import kotlinx.parcelize.Parcelize


@Parcelize
class UserWithNotes(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "name",
        entityColumn = "user"
    , entity = Note::class)
    val notes: List<Note>
) : Parcelable


