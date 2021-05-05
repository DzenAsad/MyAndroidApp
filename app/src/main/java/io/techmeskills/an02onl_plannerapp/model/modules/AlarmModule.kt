package io.techmeskills.an02onl_plannerapp.model.modules

import android.content.Context
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.alarm.MyAlarmManager
import java.text.SimpleDateFormat
import java.util.*

class AlarmModule(
    private val myAlarmManager: MyAlarmManager,
    private val context: Context,
) {
    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    fun setAlarm(note: Note) {
        myAlarmManager.setAlarm(context, dateFormatter.parse(note.date)!!.time, note.title)
    }

    fun cancelAlarm() {
        myAlarmManager.cancelAlarm(context)
    }

}