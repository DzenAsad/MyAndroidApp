package io.techmeskills.an02onl_plannerapp.model.alarm

import android.R
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat


class MyAlarmManager {

    private var pendingIntent: PendingIntent? = null

    fun setAlarm(context: Context, alarmTime: Long, message: String) {
        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, MyIntentService::class.java)
        intent.action = MyIntentService.ACTION_SEND_TEST_MESSAGE
        intent.putExtra(MyIntentService.EXTRA_MESSAGE, message)

        pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)

        val builder = NotificationCompat.Builder(context, message)
        builder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.sym_def_app_icon)
            .setContentTitle("Note")
            .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())
    }

    fun cancelAlarm(context: Context) {
        pendingIntent?.let {
            val alarmManager: AlarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(it)
        }
    }


}