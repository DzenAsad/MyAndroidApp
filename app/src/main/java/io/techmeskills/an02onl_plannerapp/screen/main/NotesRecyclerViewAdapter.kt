package io.techmeskills.an02onl_plannerapp.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.techmeskills.an02onl_plannerapp.model.Note
import io.techmeskills.an02onl_plannerapp.R


class NotesRecyclerViewAdapter(
    private val onClick: (Note) -> Unit,
    private val onDelete: (Note) -> Unit,
    private val onAdd: () -> Unit,
) : ListAdapter<Note, RecyclerView.ViewHolder>(NoteAdapterDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType){
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

    val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            if (viewHolder.itemViewType == 1) return 0 //Protect add button from delete
            return super.getMovementFlags(recyclerView, viewHolder)
        }



        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPos = viewHolder.adapterPosition
            val toPos = target.adapterPosition
            recyclerView.adapter!!.notifyItemMoved(fromPos, toPos)
            return true
        }

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            val position = viewHolder.adapterPosition
            onDelete(getItem(position))
        }

    }


    inner class NoteViewHolder(
        itemView: View,
        private val onItemClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        private val ivCloud = itemView.findViewById<ImageView>(R.id.syncImage)

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(item: Note) {
            tvTitle.text = item.title
            tvDate.text = item.date
            ivCloud.isVisible = item.fromCloud
        }
    }

    inner class AddViewHolder(
        itemView: View,
        private val onItemClick: () -> Unit
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
        return oldItem.date == newItem.date && oldItem.title == newItem.title && oldItem.fromCloud == newItem.fromCloud
    }
}