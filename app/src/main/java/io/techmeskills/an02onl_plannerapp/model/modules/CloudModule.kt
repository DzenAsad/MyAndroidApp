package io.techmeskills.an02onl_plannerapp.model.modules

import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.cloud.ApiInterface
import io.techmeskills.an02onl_plannerapp.model.cloud.CloudNote
import io.techmeskills.an02onl_plannerapp.model.cloud.CloudUser
import io.techmeskills.an02onl_plannerapp.model.cloud.ExportNotesRequestBody
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first

class CloudModule(
    private val apiInterface: ApiInterface,
    private val noteModule: NoteModule,
    private val userModule: UserModule
) {

    @ExperimentalCoroutinesApi
    suspend fun exportNotes(): Boolean {
        val user = userModule.getCurrentUserFlow().first()
        val notes = noteModule.getCurrentUserNotes()
        val cloudUser =
            CloudUser(userName = user.name)
        val cloudNotes = notes.map { CloudNote(id = it.id, title = it.title, date = it.date, alarmEnabled = it.alarmEnabled) }
        val exportRequestBody =
            ExportNotesRequestBody(cloudUser, userModule.phoneId, cloudNotes)
        val exportResult = apiInterface.exportNotes(exportRequestBody).isSuccessful
        if (exportResult) {
            noteModule.setAllNotesSyncWithCloud()
        }
        return exportResult
    }

    @ExperimentalCoroutinesApi
    suspend fun importNotes(): Boolean {
        val user = userModule.getCurrentUserFlow().first()
        val response = apiInterface.importNotes(
            userName = user.name,
            userModule.phoneId
        )
        val cloudNotes = response.body() ?: emptyList()
        val notes = cloudNotes.map { cloudNote ->
            Note(
                title = cloudNote.title,
                date = cloudNote.date,
                user = user.name,
                alarmEnabled = cloudNote.alarmEnabled,
                fromCloud = true
            )
        }
        val currNotes = noteModule.getCurrentUserNotes()
        noteModule.saveNotes(notes.toMutableList().apply { removeAll(currNotes) })
        return response.isSuccessful
    }

}