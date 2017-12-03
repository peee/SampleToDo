package com.example.peee.sampletodo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * A Room DB that holds [ToDoEntity], accessed by [ToDoDao].
 * [getInstance] has to be used to get the instance.
 */
@Database(entities = arrayOf(ToDoEntity::class), version = 1)
abstract class ToDoDatabase : RoomDatabase() {

    /**
     * Overridden by Room annotation processing.
     */
    abstract fun todoDao(): ToDoDao

    companion object {
        private const val DB_NAME = "todo.db"
        private var instance: ToDoDatabase? = null

        /**
         * Gets the single instance of [ToDoDatabase].
         *
         * @param context context to build Room database
         */
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            return buildDatabase(context).also{ instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context, ToDoDatabase::class.java, DB_NAME)
                        .allowMainThreadQueries() // TODO: init in non-main thread
                        .build()
    }
}