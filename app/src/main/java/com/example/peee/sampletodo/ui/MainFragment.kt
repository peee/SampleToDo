package com.example.peee.sampletodo.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.example.peee.sampletodo.R
import com.example.peee.sampletodo.alarm.AlarmHelper
import com.example.peee.sampletodo.db.ToDoEntity
import com.example.peee.sampletodo.ui.dialog.DeleteConfirmationDialogFragment
import com.example.peee.sampletodo.ui.dialog.ToDoDetailDialogFragment


/**
 * A fragment that shows list of all to-do items synchronized with DB,
 * interacting with user to add, edit, or delete to-do items.
 */
class MainFragment : Fragment(),
        ToDoDetailDialogFragment.Callback,
        DeleteConfirmationDialogFragment.Callback,
        LoaderManager.LoaderCallbacks<List<ToDoEntity>> {

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                     savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_main, container, false)

        setHasOptionsMenu(true)

        queryDb(ToDoDbTaskLoader.SYNC)

        view.findViewById<RecyclerView>(R.id.fragment_main_todo_list).apply {
            adapter = toDoAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when (item.itemId) {
            R.id.action_add_todo -> ToDoDetailDialogFragment().show(childFragmentManager, "todo_dialog")
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    companion object {
        const val EXTRA_TODO_DB_OP = "todo_db_op"
        const val EXTRA_TODO = "todo"
    }

    private fun queryDb(opType: Int, todo: ToDoEntity? = null) {
        Bundle().apply {
            putInt(EXTRA_TODO_DB_OP, opType)
            todo?.let{ putSerializable(EXTRA_TODO, it) }
        }.also {
            loaderManager.restartLoader(0, it, this) // force sync
        }
    }

    override fun onToDoDialogComplete(todo: ToDoEntity) {
        queryDb(ToDoDbTaskLoader.INSERT_OR_UPDATE, todo)
        if (todo.reminder > System.currentTimeMillis()) {
            AlarmHelper.set(activity, todo)
        } else {
            AlarmHelper.cancel(activity, todo)
        }
    }

    override fun onDeleteConfirmed(todo: ToDoEntity) {
        AlarmHelper.cancel(activity, todo)
        queryDb(ToDoDbTaskLoader.DELETE, todo)
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<ToDoEntity>> {
        val op = args?.getInt(EXTRA_TODO_DB_OP) ?: ToDoDbTaskLoader.SYNC
        val data = args?.getSerializable(EXTRA_TODO) as? ToDoEntity ?: ToDoEntity()
        return ToDoDbTaskLoader(activity, op, data)
    }

    override fun onLoadFinished(loader: Loader<List<ToDoEntity>>?, data: List<ToDoEntity>?) {
        data?.let { toDoAdapter.setToDoList(it) }
    }

    override fun onLoaderReset(loader: Loader<List<ToDoEntity>>?) {
    }

}// Required empty public constructor
