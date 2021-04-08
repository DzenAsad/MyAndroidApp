package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainViewModel : CoroutineViewModel() {


    val notes = mutableListOf(
        Note("Помыть посуду"),
        Note("Забрать пальто из химчистки", "23.03.2021"),
        Note("Позвонить Ибрагиму"),
        Note("Заказать перламутровые пуговицы"),
        Note("Избить соседа за шум ночью"),
        Note("Выпить на неделе с Володей", "22.03.2021"),
        Note("Починить кран"),
        Note("Выбить ковры перед весной"),
        Note("Заклеить сапог жене"),
        Note("Купить картошки"),
        Note("Скачать кино в самолёт", "25.03.2021")
    )

    val listLiveData = MutableLiveData(notes)


    fun addNoteToList(string: String, date: String?) {
        launch {
            listLiveData.value?.add(0, Note(string, date))
        }

    }

    fun replaceNote(string: String, date: String?, note: Note) {
        launch {
            note.title = string
            note.date = date
        }
    }
}

class Note(
    var title: String,
    var date: String? = null
)



