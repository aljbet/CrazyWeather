package com.example.crazyweather.models.vmmodels

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics

sealed class CurrentWeatherState {
    object Loading : CurrentWeatherState()
    data class Success(
        val cityName: String,
        val currentWeather: WeatherMetrics
    ) : CurrentWeatherState()
    data class Error(val message: String) : CurrentWeatherState()
}