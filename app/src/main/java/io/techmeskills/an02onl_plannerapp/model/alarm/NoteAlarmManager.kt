package io.techmeskills.an02onl_plannerapp.model.alarm

import android.app.AlarmManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import io.techmeskills.an02onl_plannerapp.model.Note


class NoteAlarmManager(private val context: Context) {
    //Alarm manager
    private val alarmManager = getSystemService(context, AlarmManager::class.java) as AlarmManager
    private val noteIntent: NoteIntent = NoteIntent()

    //Set Alarm
    fun setAlarm(alarmTime: Long, note: Note) {

        alarmManager.setExact(AlarmManager.RTC, alarmTime, noteIntent.buildIntent(context, note))
//        Toast.makeText(context, "Alarm is set", Toast.LENGTH_SHORT).show()


    }


    fun cancelAlarm(note: Note) {
        alarmManager.cancel(noteIntent.buildIntent(context, note))
    }


}