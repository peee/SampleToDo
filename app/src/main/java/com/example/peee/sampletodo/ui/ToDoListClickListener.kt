package com.example.peee.sampletodo.ui

import com.example.peee.sampletodo.db.ToDoEntity

/**
 * An interface to handle click events on to-do item
 */
interface ToDoListClickListener {
    fun onClick(todo: ToDoEntity)
    fun onLongClick(todo: ToDoEntity): Boolean = false
}