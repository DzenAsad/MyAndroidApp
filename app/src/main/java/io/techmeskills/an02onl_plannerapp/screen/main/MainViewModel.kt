package io.techmeskills.an02onl_plannerapp.screen.main

import androidx.lifecycle.MutableLiveData
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainViewModel : CoroutineViewModel() {

    val listLiveData = MutableLiveData<MutableList<Note>>()

    init {
        launch {
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
            listLiveData.postValue(notes)
        }
    }

    fun addNoteToList(string: String) {
        launch {
            val note = Note(string)
            listLiveData.value?.add(note)
        }
    }
}

class Note(
        val title: String,
        val date: String? = null
)



