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
import com.example.peee.sampletodo.alarm.AlarmHelper
import com.example.peee.sampletodo.db.ToDoDatabase
import com.example.peee.sampletodo.db.ToDoEntity
import com.example.peee.sampletodo.ui.dialog.DeleteConfirmationDialogFragment
import com.example.peee.sampletodo.ui.dialog.ToDoDetailDialogFragment


/**
 * A fragment that shows list of all to-do items synchronized with DB,
 * interacting with user to add, edit, or delete to-do items.
 */
class MainFragment : Fragment(),
        ToDoDetailDialogFragment.Callback,
        DeleteConfirmationDialogFragment.Callback {

    private val toDoAdapter = ToDoListAdapter(object : ToDoListClickListener {
        override fun onClick(todo: ToDoEntity) {
            ToDoDetailDialogFragment.createFrom(todo)
                    .show(childFragmentManager, "todo_dialog_update")
        }

        override fun onLongClick(todo: ToDoEntity): Boolean {
            DeleteConfirmationDialogFragment.createFrom(todo)
                    .show(childFragmentManager, "todo_dialog_delete")
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
                ToDoDetailDialogFragment().show(childFragmentManager, "todo_dialog")
            }
        }

        return view
    }

    private fun syncToDoListWithDb() {
        val toDoList = toDoDb.todoDao().loadAllItems()
        toDoAdapter.setToDoList(toDoList)
        toDoAdapter.notifyDataSetChanged()
    }

    override fun onToDoDialogComplete(todo: ToDoEntity) {
        toDoDb.todoDao().insertOrUpdate(todo)
        if (todo.reminder > System.currentTimeMillis()) {
            AlarmHelper.set(activity, todo)
        } else {
            AlarmHelper.cancel(activity, todo)
        }
        syncToDoListWithDb()
    }

    override fun onDeleteConfirmed(todo: ToDoEntity) {
        toDoDb.todoDao().delete(todo)
        syncToDoListWithDb()
    }
}// Required empty public constructor
