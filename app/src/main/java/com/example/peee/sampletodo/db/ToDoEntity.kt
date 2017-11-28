package com.example.peee.sampletodo.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "todo")
data class ToDoEntity(var title: String = "",
                 var description: String = "",
                 var dueDate: Long = 0,
                 var reminder: Long = 0) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
