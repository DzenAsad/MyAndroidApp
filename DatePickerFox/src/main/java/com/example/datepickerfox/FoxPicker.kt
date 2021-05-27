package com.example.datepickerfox

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class FoxPicker @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val rvFoxPicker: RecyclerView by lazy { findViewById(R.id.rvFoxPicker) }

    init {
        View.inflate(context, R.layout.date_picker_fox, this)
    }

    fun setDate(list: List<String>, onDateChangeCallback: (Date) -> Unit) {
        val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val listDate = mutableSetOf<Date>()
        list.forEach { if (it.isNotBlank()) listDate.add(dateFormatter.parse(it)!!) }
        rvFoxPicker.adapter = DaysAdapter(listDate.toList(), onDateChangeCallback)
    }

    class DaysAdapter(
        private val items: List<Date>,
        private val onDateChangeCallback: ((Date) -> Unit),
    ) :
        RecyclerView.Adapter<DayViewHolder>() {

        private var selectedDate: Date? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder =
            DayViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.date_sample, parent, false),
                ::onItemClick)

        private fun onItemClick(pos: Int) {
            onDateChangeCallback(items[pos])
        }

        override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
            holder.bind(items[position], selectedDate == items[position])
        }

        override fun getItemCount(): Int = items.size


    }

    class DayViewHolder(itemView: View, onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        private val tvDay = itemView.findViewById<TextView>(R.id.tvDay)

        init {
            itemView.setOnClickListener {
                onClick(adapterPosition)
            }
        }

        fun bind(date: Date, selected: Boolean) {
            tvDate.text = monthDayFormatter.format(date)
            tvDay.text = weekDayFormatter.format(date)
        }

        companion object {
            val monthDayFormatter = SimpleDateFormat("dd", Locale.getDefault())
            val weekDayFormatter = SimpleDateFormat("EEE", Locale.getDefault())
        }
    }

}