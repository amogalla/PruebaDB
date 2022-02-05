package com.example.android.gestiondeentradas.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Defines methods for using the Entrada class with Room.
 */
@Dao
interface EntradaDatabaseDao {

    @Insert
    suspend fun insert(entrada: Entrada)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */
    @Update
    suspend fun update(entrada: Entrada)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from entrada_table WHERE id_entrada = :key")
    suspend fun get(key: Long): Entrada?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM entrada_table")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM entrada_table ORDER BY id_entrada DESC")
    fun getAllNights(): LiveData<List<Entrada>>

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM entrada_table ORDER BY id_entrada DESC LIMIT 1")
    suspend fun getEntrada(): Entrada?

}

