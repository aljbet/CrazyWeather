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
                    timestamp = System.currentTimeMillis() - 86400000,
                    results = listOf(
                        CityWeather(
                            cityName = "Москва",
                            metrics = WeatherMetrics(22, 8, 70, 90),
                            matchPercentage = 85
                        ),
                        CityWeather(
                            cityName = "Санкт-Петербург",
                            metrics = WeatherMetrics(20, 12, 80, 95),
                            matchPercentage = 78
                        )
                    )
                ),
                SearchHistoryItem(
                    id = nextId++,
                    searchParams = WeatherMetrics(
                        temperature = 5,
                        windSpeed = 20,
                        humidity = 20,
                        cloudiness = 100
                    ),
                    timestamp = System.currentTimeMillis() - 172800000,
                    results = listOf(
                        CityWeather(
                            cityName = "Новосибирск",
                            metrics = WeatherMetrics(3, 18, 25, 100),
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