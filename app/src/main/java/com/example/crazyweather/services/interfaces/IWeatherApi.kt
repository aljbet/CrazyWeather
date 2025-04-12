package com.example.crazyweather.services.interfaces

import com.example.crazyweather.models.entities.WeatherMetrics

interface IWeatherApi {
    suspend fun getCityForecast(cityName: String, duringDays: Int): List<WeatherMetrics>
}