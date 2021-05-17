package io.techmeskills.an02onl_plannerapp.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.model.Note


class NotesRecyclerViewAdapter(
    private val onClick: (Note) -> Unit,
    private val onAdd: () -> Unit,
) : ListAdapter<Note, RecyclerView.ViewHolder>(NoteAdapterDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder = when (viewType) {
        ADD -> AddViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item_add, parent, false),
            onAdd
        )

        else -> NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false),
            ::onItemClick
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AddNote -> ADD
            else -> NOTE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoteViewHolder -> holder.bind(getItem(position))
            else -> (holder as AddViewHolder).bind()
        }
    }


    private fun onItemClick(position: Int) {
        onClick(getItem(position))
    }


    inner class NoteViewHolder(
        itemView: View,
        private val onItemClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        private val ivCloud = itemView.findViewById<ImageView>(R.id.syncImage)
        private val ivAlarm = itemView.findViewById<ImageView>(R.id.alarmImage)

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(item: Note) {
            tvTitle.text = item.title
            tvDate.text = item.date
            ivCloud.isVisible = item.fromCloud
            ivAlarm.isVisible = item.alarmEnabled
        }
    }

    inner class AddViewHolder(
        itemView: View,
        private val onItemClick: () -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClick()
            }
        }

        fun bind() = Unit
    }


    companion object {
        const val ADD = 1
        const val NOTE = 2
    }
}

class NoteAdapterDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.date == newItem.date && oldItem.title == newItem.title && oldItem.fromCloud == newItem.fromCloud && oldItem.alarmEnabled == newItem.alarmEnabled
    }
}