package io.techmeskills.an02onl_plannerapp.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.techmeskills.an02onl_plannerapp.R


class NotesRecyclerViewAdapter(
    private val onClick: (Note) -> Unit,
    private val onDelete: (Int) -> Unit,
    private val onAdd: (Int) -> Unit
) :
    ListAdapter<Note, NotesRecyclerViewAdapter.NoteViewHolder>(NoteAdapterDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false),
            ::onItemClick
        )
    }


    override fun onBindViewHolder(holder: NotesRecyclerViewAdapter.NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    private fun onItemClick(position: Int) {
        val a = currentList.size - 1
        if (position == a)
            onAdd(a)
        else onClick(getItem(position))
    }

    val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            //Remove swiped item from list and notify the RecyclerView
            val position = viewHolder.adapterPosition
            onDelete(position)
        }
    }


    inner class NoteViewHolder(
        itemView: View,
        private val onItemClick: (Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(item: Note) {
            tvTitle.text = item.title
            tvDate.text = item.date
        }
    }
}

class NoteAdapterDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.date == newItem.date && oldItem.title == newItem.title
    }
}