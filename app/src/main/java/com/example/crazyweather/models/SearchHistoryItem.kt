package com.example.crazyweather.models

data class SearchHistoryItem(
    val id: Long,
    val searchParams: WeatherMetrics,
    val timestamp: Long,
    val results: List<CityWeather>?
)