package com.example.peee.sampletodo.ui

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.example.peee.sampletodo.db.ToDoDatabase
import com.example.peee.sampletodo.db.ToDoEntity

/**
 * A loader to access to-do database in background.
 * [opType] should be specified from [SYNC], [INSERT_OR_UPDATE], and [DELETE]
 */
class ToDoDbTaskLoader(context: Context, private val opType: Int, private val todo: ToDoEntity)
    : AsyncTaskLoader<List<ToDoEntity>>(context) {

    companion object {
        const val SYNC = 0
        const val INSERT_OR_UPDATE = 1
        const val DELETE = 2
    }

    private var isInProgress = false

    override fun onStartLoading() {
        if (!isInProgress) {
            forceLoad()
        }
    }

    override fun onForceLoad() {
        isInProgress = true
        super.onForceLoad()
    }

    override fun loadInBackground(): List<ToDoEntity> {
        val db = ToDoDatabase.getInstance(context)
        when (opType) {
            INSERT_OR_UPDATE -> db.todoDao().insertOrUpdate(todo)
            DELETE -> db.todoDao().delete(todo)
        }
        return db.todoDao().loadAllItems()
    }
}