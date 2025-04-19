package com.example.crazyweather.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryItemTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): HistoryDao
}