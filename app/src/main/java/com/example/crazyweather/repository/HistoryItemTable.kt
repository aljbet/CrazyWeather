package com.example.crazyweather.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryItemTable(
    @PrimaryKey val id: Long,
    val temperature: Int,
    val windSpeed: Int,
    val humidity: Int,
    val cloudiness: Int,
    val timestamp: Long
)