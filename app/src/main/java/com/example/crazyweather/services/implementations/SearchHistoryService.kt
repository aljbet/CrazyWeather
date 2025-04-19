package com.example.crazyweather.services.implementations

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.SearchHistoryItem
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.repository.HistoryDao
import com.example.crazyweather.repository.HistoryItemTable
import com.example.crazyweather.services.interfaces.ISearchHistoryService

class SearchHistoryService(val historyDao: HistoryDao) : ISearchHistoryService {
    private var nextId: Long = 1

    override suspend fun saveSearch(searchParams: WeatherMetrics, results: List<CityWeather>?) {
        historyDao.insert(
            HistoryItemTable(
                id = nextId,
                temperature = searchParams.temperature ?: 0,
                windSpeed = searchParams.windSpeed ?: 0,
                humidity = searchParams.humidity ?: 0,
                cloudiness = searchParams.cloudiness ?: 0,
                timestamp = System.currentTimeMillis()
            )
        )
        nextId++
    }

    override suspend fun getSearchHistory(): List<SearchHistoryItem> {
        return historyDao.getAll().map {
            SearchHistoryItem(
                it.id,
                WeatherMetrics(it.temperature, it.windSpeed, it.humidity, it.cloudiness),
                it.timestamp
            )
        }
    }

    override suspend fun clearHistory() {
        historyDao.deleteAll()
        nextId = 1
    }
}