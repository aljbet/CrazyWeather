package com.example.crazyweather.services.interfaces

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics

interface IWeatherApiService {
    suspend fun getCitiesAverageWeather(duringDays: Int): List<CityWeather>
    suspend fun getCityForecast(cityName: String, duringDays: Int): List<WeatherMetrics>
    suspend fun getCityAverageForecast(cityName: String, duringDays: Int): CityWeather
}