package io.techmeskills.an02onl_plannerapp.model.alarm

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.model.Note

class NoteIntent {

    fun buildIntent(context: Context, note: Note): PendingIntent{
        //Intent
        val intent = Intent(context, NoteAlarmReceiver::class.java)
        //put our info in intent
//        intent.extras?.putParcelable("ALARM_NOTE", note)
//        intent.putExtra("ALARM_NOTE", note)
        intent.putExtra("ALARM_MSG", note.title)
        intent.putExtra("ALARM_ID", note.id)
        //PendingIntent
        return PendingIntent.getBroadcast(context, 1221, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun makeDeleteAction(context: Context, noteId: Long): NotificationCompat.Action {
        val deleteIntent =
            Intent(context.applicationContext, NoteAlarmService::class.java)
        deleteIntent.action = "NoteDelete"
        deleteIntent.putExtra("NoteDeleteId", noteId)

        val deletePendingIntent = PendingIntent.getService(
            context.applicationContext,
            1221,
            deleteIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Action.Builder(
            R.drawable.ic_baseline_note_24,
            "Delete",
            deletePendingIntent
        ).build()
    }
}