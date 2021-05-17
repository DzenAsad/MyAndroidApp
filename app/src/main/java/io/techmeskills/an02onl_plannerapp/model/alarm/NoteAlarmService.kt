package io.techmeskills.an02onl_plannerapp.model.alarm

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import io.techmeskills.an02onl_plannerapp.model.modules.NoteModule
import io.techmeskills.an02onl_plannerapp.model.receiver.NoteAlarmReceiver
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class NoteAlarmService : Service(), KoinComponent {
    private val noteModule: NoteModule by inject()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val noteId =
                it.getLongExtra(NoteIntent.NOTE_CURRENT_ID, -1)
            when (it.action) {
                NoteIntent.NOTE_DELETE -> {
                    GlobalScope.launch {
                        noteModule.deleteNoteById(noteId)
                    }
                    nm.cancel(NoteAlarmReceiver.NOTIFICATION_ID)
                }
                NoteIntent.NOTE_POSTPONE -> {
                    GlobalScope.launch {
                        noteModule.postponeNote(noteId)
                    }
                    nm.cancel(NoteAlarmReceiver.NOTIFICATION_ID)
                }
                else -> Unit
            }
            stopSelf()
        }
        return START_NOT_STICKY
    }
}