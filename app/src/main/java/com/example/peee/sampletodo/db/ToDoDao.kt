package com.example.peee.sampletodo.db

import android.arch.persistence.room.*

/**
 * A DAO: Data Access Object for [ToDoEntity] and [ToDoDatabase] using the Room
 */
@Dao
interface ToDoDao {
    /**
     * Loads all to-do items from the DB.
     *
     * @return list of to-do items in the DB
     */
    @Query("SELECT * FROM todo")
    fun loadAllItems(): List<ToDoEntity>

    /**
     * Loads a to-do item that matches given ID.
     *
     * @param id id of to-do item in the DB
     * @return to-do item matched
     */
    @Query("SELECT * FROM todo where id = :arg0")
    fun loadItem(id: Long): ToDoEntity

    /**
     * Inserts given to-do item in the DB if its ID doesn't exist,
     * or updates if exists
     *
     * @param todo to-do item to insert or update
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(todo: ToDoEntity)

    /**
     * Deletes given to-do item in the DB.
     *
     * @param todo to-do item to delete
     */
    @Delete
    fun delete(todo: ToDoEntity)
}