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
                        temperature = 25.0,
                        windSpeed = 10.0,
                        humidity = 75.0,
                        cloudiness = 100.0
                    ),
                    timestamp = System.currentTimeMillis() - 86400000,
                    results = listOf(
                        CityWeather(
                            cityName = "Москва",
                            metrics = WeatherMetrics(22.0, 8.0, 70.0, 90.0),
                            matchPercentage = 85
                        ),
                        CityWeather(
                            cityName = "Санкт-Петербург",
                            metrics = WeatherMetrics(20.0, 12.0, 80.0, 95.0),
                            matchPercentage = 78
                        )
                    )
                ),
                SearchHistoryItem(
                    id = nextId++,
                    searchParams = WeatherMetrics(
                        temperature = 5.0,
                        windSpeed = 20.0,
                        humidity = 20.0,
                        cloudiness = 100.0
                    ),
                    timestamp = System.currentTimeMillis() - 172800000,
                    results = listOf(
                        CityWeather(
                            cityName = "Новосибирск",
                            metrics = WeatherMetrics(3.0, 18.0, 25.0, 100.0),
                            matchPercentage = 92
                        )
                    )
                )
            )
        )
    }

    override suspend fun saveSearch(searchParams: WeatherMetrics, results: List<CityWeather>?) {
        history.add(
            SearchHistoryItem(
                id = nextId++,
                searchParams = searchParams,
                timestamp = System.currentTimeMillis(),
                results = results
            )
        )
    }

    override suspend fun getSearchHistory(): List<SearchHistoryItem> {
        println("History items: ${history.size}")
        return history.sortedByDescending { it.timestamp }.toList()
    }

    override suspend fun clearHistory() {
        history.clear()
        nextId = 1
    }
}