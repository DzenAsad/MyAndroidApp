package io.techmeskills.an02onl_plannerapp.model.modules

import android.content.Context
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.alarm.MyAlarmManager

class AlarmModule(
    private val myAlarmManager: MyAlarmManager,
    private val context: Context,
) {

    fun setAlarm(note: Note) {
        myAlarmManager.setAlarm(context, note.date.toLong(), note.title)
    }

    fun cancelAlarm() {
        myAlarmManager.cancelAlarm(context)
    }

}