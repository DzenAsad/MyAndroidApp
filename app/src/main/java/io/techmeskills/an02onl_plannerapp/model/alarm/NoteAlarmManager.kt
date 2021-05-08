package io.techmeskills.an02onl_plannerapp.model.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService


class NoteAlarmManager {


    fun setAlarm(context: Context, alarmTime: Long, message: String) {

        //Intent
        val intent = Intent(context, NoteAlarmReceiver::class.java)
        intent.putExtra("ALARM_MSG", message); //put our info in intent

        //PendingIntent
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        //Alarm manager
        val alarmManager = getSystemService(context, AlarmManager::class.java) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC, alarmTime, pendingIntent)
//        Toast.makeText(context, "Alarm is set", Toast.LENGTH_SHORT).show()


    }


    fun cancelAlarm(context: Context) {

    }





}