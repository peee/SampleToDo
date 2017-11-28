package com.example.peee.sampletodo.ui

import com.example.peee.sampletodo.db.ToDoEntity

interface ToDoListClickListener {
    fun onClick(todo: ToDoEntity)
    fun onLongClick(todo: ToDoEntity): Boolean = false
}