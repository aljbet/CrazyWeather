package com.example.crazyweather.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert
    fun insert(item: HistoryItemTable)

    @Query("SELECT * FROM historyitemtable")
    fun getAll() : List<HistoryItemTable>

    @Delete
    fun deleteAll()
}