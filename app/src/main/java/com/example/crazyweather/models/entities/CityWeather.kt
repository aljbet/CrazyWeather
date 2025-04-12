package com.example.crazyweather.models.entities

data class CityWeather(
    val cityName: String,
    val metrics: WeatherMetrics,
    var matchPercentage: Int = 0
)