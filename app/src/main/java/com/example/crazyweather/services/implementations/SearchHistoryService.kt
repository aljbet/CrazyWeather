package com.example.crazyweather.services.implementations

import com.example.crazyweather.models.CityWeather
import com.example.crazyweather.models.SearchHistoryItem
import com.example.crazyweather.models.WeatherMetrics
import com.example.crazyweather.services.interfaces.ISearchHistoryService

class SearchHistoryService : ISearchHistoryService {
    private val history = mutableListOf<SearchHistoryItem>()
    private var nextId: Long = 1

    override suspend fun saveSearch(searchParams: WeatherMetrics, results: List<CityWeather>?) {
        history.add(
            SearchHistoryItem(
                id = nextId,
                searchParams = searchParams,
                timestamp = System.currentTimeMillis(),
                results = results
            )
        )
        nextId++
    }

    override suspend fun getSearchHistory(): List<SearchHistoryItem> {
        return history.toList()
    }

    override suspend fun clearHistory() {
        history.clear()
    }
}