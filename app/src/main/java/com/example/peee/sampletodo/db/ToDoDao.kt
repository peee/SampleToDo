package com.example.peee.sampletodo.db

import android.arch.persistence.room.*

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun loadAllItems(): List<ToDoEntity>

    @Query("SELECT * FROM todo where id = :arg0")
    fun loadItem(id: Long): ToDoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: ToDoEntity)

    @Delete
    fun delete(todo: ToDoEntity)
}