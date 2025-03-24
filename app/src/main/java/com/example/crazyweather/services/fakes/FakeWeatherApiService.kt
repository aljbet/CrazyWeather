package com.example.crazyweather.services.fakes

import com.example.crazyweather.models.CityWeather
import com.example.crazyweather.models.WeatherMetrics
import com.example.crazyweather.services.interfaces.IWeatherApiService

class FakeWeatherApiService : IWeatherApiService {
    override suspend fun getCitiesAverageWeather(duringDays: Int): List<CityWeather> {
        TODO("Not yet implemented")
    }

    override suspend fun getCityForecast(cityName: String, duringDays: Int): List<WeatherMetrics> {
        TODO("Not yet implemented")
    }

    override suspend fun getCityAverageForecast(cityName: String, duringDays: Int): CityWeather {
        TODO("Not yet implemented")
    }
}