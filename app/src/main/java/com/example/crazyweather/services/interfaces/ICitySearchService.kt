package com.example.crazyweather.services.interfaces

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics

interface ICitySearchService {
    suspend fun findBestMatchingCities(userMetrics: WeatherMetrics): List<CityWeather>
    suspend fun getCityForecast(cityName: String, duringDays: Int): List<WeatherMetrics>
}