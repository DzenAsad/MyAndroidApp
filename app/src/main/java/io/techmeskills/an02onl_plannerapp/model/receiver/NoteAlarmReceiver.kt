package io.techmeskills.an02onl_plannerapp.model.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.model.alarm.NoteIntent

class NoteAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        pushNote(context, intent)

    }

    private fun pushNote(context: Context, intent: Intent) {
        val noteIntent: NoteIntent = NoteIntent()

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //Notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                CHANNEL_ID,
                MY_APP_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            nm.createNotificationChannel(mChannel)
        }
        //get Note from intent
//        val tmpNote: Note? = intent.extras?.getParcelable("ALARM_NOTE")
        val noteTitle = intent.getStringExtra("ALARM_MSG")
        val noteId = intent.getLongExtra("ALARM_ID", -1)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .addAction(noteIntent.makeDeleteAction(context, noteId))
            .addAction(noteIntent.makePostponeAction(context, noteId))
            .setSmallIcon(R.drawable.ic_baseline_note_24)
            .setContentTitle("Напоминание")
            .setContentText(noteTitle)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        //Push notification
        nm.notify(NOTIFICATION_ID, builder.build())
//        with(NotificationManagerCompat.from(context)) {
//            notify(NOTIFICATION_ID, builder.build())
//            // посылаем уведомление
//        }





    }

    companion object {
        const val NOTIFICATION_ID = 2332
        const val CHANNEL_ID = "MyApp_channel"
        const val MY_APP_CHANNEL_NAME = "MyApp"
    }
}