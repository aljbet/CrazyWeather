package com.example.crazyweather.models.entities

data class SearchHistoryItem(
    val id: Long,
    val searchParams: WeatherMetrics,
    val timestamp: Long,
    val results: List<CityWeather>?
)