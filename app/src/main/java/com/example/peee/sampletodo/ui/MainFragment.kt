package com.example.peee.sampletodo.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.peee.sampletodo.R
import com.example.peee.sampletodo.db.ToDoDatabase
import com.example.peee.sampletodo.db.ToDoEntity
import java.util.concurrent.TimeUnit


class MainFragment : Fragment() {
    companion object {
        private const val LOG_TAG = "MainFragment"
    }

    private val toDoAdapter = ToDoListAdapter(object : ToDoListClickListener {
        override fun onClick(todo: ToDoEntity) {
            Log.i(LOG_TAG, "clicked $todo")
            // TODO: open detail dialog
        }

        override fun onLongClick(todo: ToDoEntity): Boolean {
            Log.i(LOG_TAG, "long clicked $todo")
            // TODO: open dialog to delete
            toDoDb.todoDao().delete(todo)
            syncToDoListWithDb()
            return true
        }
    })

    private lateinit var toDoDb: ToDoDatabase

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                     savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_main, container, false)

        toDoDb = ToDoDatabase.getInstance(activity)

        syncToDoListWithDb()

        view.findViewById<RecyclerView>(R.id.fragment_main_todo_list).apply {
            adapter = toDoAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        view.findViewById<Button>(R.id.fragment_main_button_add).apply {
            setOnClickListener {
                // TODO: open dialog for addition
                val current = System.currentTimeMillis()
                val todo = ToDoEntity("hoge", "hogefuga",
                        current + TimeUnit.DAYS.toMillis(2),
                        current + TimeUnit.DAYS.toMillis(1))
                toDoDb.todoDao().insert(todo)
                syncToDoListWithDb()
            }
        }

        return view
    }

    private fun syncToDoListWithDb() {
        val toDoList = toDoDb.todoDao().loadAllItems()
        toDoAdapter.setToDoList(toDoList)
        toDoAdapter.notifyDataSetChanged()
    }
}// Required empty public constructor
