package io.techmeskills.an02onl_plannerapp.model.alarm

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import io.techmeskills.an02onl_plannerapp.model.Note

object NoteIntent {

    fun buildIntent(context: Context, note: Note): PendingIntent{
        //Intent
        val intent = Intent(context, NoteAlarmReceiver::class.java)
        intent.putExtra("ALARM_MSG", note.title) //put our info in intent

        //PendingIntent
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }
}