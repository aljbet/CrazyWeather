package com.example.crazyweather.services.implementations

import com.example.crazyweather.models.SupportedCities
import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.services.interfaces.IWeatherApi
import com.example.crazyweather.services.interfaces.IWeatherService

class WeatherService (
    private val weatherApi: IWeatherApi
) : IWeatherService {
    override suspend fun getCitiesAverageWeather(duringDays: Int): List<CityWeather> {
        return SupportedCities.cities.map { city ->
            getCityAverageForecast(city.name, duringDays)
        }
    }

    override suspend fun getCityForecast(cityName: String, duringDays: Int): List<WeatherMetrics> {
        return weatherApi.getCityForecast(cityName, duringDays)
    }

    override suspend fun getCityAverageForecast(cityName: String, duringDays: Int): CityWeather {
        val forecast = weatherApi.getCityForecast(cityName, duringDays)

        return CityWeather(
            cityName = cityName,
            metrics = calculateAverageMetrics(forecast)
        )
    }

    private fun calculateAverageMetrics(metrics: List<WeatherMetrics>): WeatherMetrics {
        return WeatherMetrics(
            temperature = metrics.map { it.temperature }.average().toInt(),
            windSpeed = metrics.map { it.windSpeed }.average().toInt(),
            humidity = metrics.map { it.humidity }.average().toInt(),
            cloudiness = metrics.map { it.cloudiness }.average().toInt()
        )
    }
}