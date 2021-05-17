package io.techmeskills.an02onl_plannerapp.model.modules

import android.content.Context
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.model.alarm.NoteAlarmManager
import java.text.SimpleDateFormat
import java.util.*

class AlarmModule(
    private val noteAlarmManager: NoteAlarmManager,
) {
    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    fun setAlarm(note: Note) {
        noteAlarmManager.setAlarm(dateFormatter.parse(note.date)!!.time, note)
    }

    fun cancelAlarm(note: Note) {
        noteAlarmManager.cancelAlarm(note)
    }

}