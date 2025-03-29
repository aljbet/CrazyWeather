package com.example.crazyweather.services.interfaces

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.SearchHistoryItem
import com.example.crazyweather.models.entities.WeatherMetrics

interface ISearchHistoryService {
    suspend fun saveSearch(searchParams: WeatherMetrics, results: List<CityWeather>?)
    suspend fun getSearchHistory(): List<SearchHistoryItem>
    suspend fun clearHistory()
}