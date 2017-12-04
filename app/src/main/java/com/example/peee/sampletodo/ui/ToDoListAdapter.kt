package com.example.peee.sampletodo.ui

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.peee.sampletodo.R
import com.example.peee.sampletodo.db.ToDoEntity
import com.example.peee.sampletodo.ui.dialog.Reminder

/**
 * A list adapter that adapts given to-do items to recycler view.
 */
class ToDoListAdapter(private val listener: ToDoListClickListener)
    : RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {

    private var toDoList = emptyList<ToDoEntity>()

    /**
     * Applies list of to-do items to the adapter to update view.
     *
     * @param toDoList list of to-do items to show
     */
    fun setToDoList(toDoList: List<ToDoEntity>) {
        DiffUtil.calculateDiff(ToDoListDiffCallback(this.toDoList, toDoList))
                .dispatchUpdatesTo(this)
        this.toDoList = toDoList
    }

    private class ToDoListDiffCallback(val old: List<ToDoEntity>, val new: List<ToDoEntity>)
        : DiffUtil.Callback() {

        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition].id == new[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition] == new[newItemPosition]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.list_todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (position < toDoList.size) {
            holder?.apply {
                setTexts(toDoList[adapterPosition])
                view.setOnClickListener { listener.onClick(toDoList[adapterPosition]) }
                view.setOnLongClickListener { listener.onLongClick(toDoList[adapterPosition]) }
            }
        }
    }

    override fun getItemCount(): Int = toDoList.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun setTexts(todo: ToDoEntity) {
            view.findViewById<TextView>(R.id.list_todo_title)?.text = todo.title
            view.findViewById<TextView>(R.id.list_todo_description)?.text = todo.description

            view.findViewById<TextView>(R.id.list_todo_due_date)?.text =
                    if (todo.dueDate == 0L) "None" else DateFormatter.toString(todo.dueDate)
            view.findViewById<TextView>(R.id.list_todo_reminder)?.text =
                    if (todo.reminder == 0L) "None" else getReminder(todo)
        }

        private fun getReminder(todo: ToDoEntity): String {
            val diff = todo.dueDate - todo.reminder
            val reminder = Reminder.millisOf(diff)
            return view.context.getString(reminder.resId)
        }
    }
}