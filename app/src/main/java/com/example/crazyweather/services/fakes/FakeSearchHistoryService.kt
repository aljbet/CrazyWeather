package com.example.crazyweather.services.fakes

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.SearchHistoryItem
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.services.interfaces.ISearchHistoryService

class FakeSearchHistoryService() : ISearchHistoryService {
    private val history = mutableListOf<SearchHistoryItem>()
    private var nextId: Long = 1

    init {
        history.addAll(
            listOf(
                SearchHistoryItem(
                    id = nextId++,
                    searchParams = WeatherMetrics(
                        temperature = 25,
                        windSpeed = 10,
                        humidity = 75,
                        cloudiness = 100
                    ),
                    timestamp = System.currentTimeMillis() - 86400000
                ),
                SearchHistoryItem(
                    id = nextId++,
                    searchParams = WeatherMetrics(
                        temperature = 5,
                        windSpeed = 20,
                        humidity = 20,
                        cloudiness = 100
                    ),
                    timestamp = System.currentTimeMillis() - 172800000
                )
            )
        )
    }

    override suspend fun saveSearch(searchParams: WeatherMetrics, results: List<CityWeather>?) {
        history.add(
            SearchHistoryItem(
                id = nextId++,
                searchParams = searchParams,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override suspend fun getSearchHistory(): List<SearchHistoryItem> {
        return history.sortedByDescending { it.timestamp }.toList()
    }

    override suspend fun clearHistory() {
        history.clear()
        nextId = 1
    }
}