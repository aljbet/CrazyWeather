package com.example.crazyweather.models

data class CityWeather(
    val cityName: String,
    val metrics: WeatherMetrics,
    val matchPercentage: Int = 0
)