package com.example.crazyweather.services.interfaces

import com.example.crazyweather.models.CityWeather
import com.example.crazyweather.models.SearchHistoryItem
import com.example.crazyweather.models.WeatherMetrics

interface ISearchHistoryService {
    suspend fun saveSearch(searchParams: WeatherMetrics, results: List<CityWeather>?)
    suspend fun getSearchHistory(): List<SearchHistoryItem>
    suspend fun clearHistory()
}