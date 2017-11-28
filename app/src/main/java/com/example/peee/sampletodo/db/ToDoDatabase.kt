package com.example.peee.sampletodo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(ToDoEntity::class), version = 1)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun todoDao(): ToDoDao

    companion object {
        private const val DB_NAME = "todo.db"
        private var instance: ToDoDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            return buildDatabase(context).also{ instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context, ToDoDatabase::class.java, DB_NAME)
                        .allowMainThreadQueries() // TODO: init in non-main thread
                        .build()
    }
}