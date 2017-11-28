package com.example.peee.sampletodo.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.peee.sampletodo.R
import com.example.peee.sampletodo.db.ToDoEntity
import java.text.SimpleDateFormat
import java.util.*

class ToDoListAdapter(private val listener: ToDoListClickListener)
    : RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {

    private var toDoList = emptyList<ToDoEntity>()

    fun setToDoList(toDoList: List<ToDoEntity>) {
        this.toDoList = toDoList
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.list_todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (position < toDoList.size) {
            holder?.apply {
                setTexts(toDoList[position])
                view.setOnClickListener { listener.onClick(toDoList[position]) }
                view.setOnLongClickListener { listener.onLongClick(toDoList[position]) }
            }
        }
    }

    override fun getItemCount(): Int = toDoList.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun setTexts(todo: ToDoEntity) {
            view.findViewById<TextView>(R.id.list_todo_title)?.text = todo.title
            view.findViewById<TextView>(R.id.list_todo_description)?.text = todo.description

            // TODO: Consider better time format
            val format = SimpleDateFormat("YYYY/MM/dd HH:mm", Locale.US)
            view.findViewById<TextView>(R.id.list_todo_due_date)?.text = format.format(Date(todo.dueDate))
            view.findViewById<TextView>(R.id.list_todo_reminder)?.text = format.format(Date(todo.reminder))
        }
    }
}