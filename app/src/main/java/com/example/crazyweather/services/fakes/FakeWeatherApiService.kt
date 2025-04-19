package com.example.crazyweather.services.fakes

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.services.interfaces.IWeatherService

class FakeWeatherApiService : IWeatherService {
    override suspend fun getCitiesAverageWeather(duringDays: Int): List<CityWeather> {
        return listOf(
            CityWeather("Сургут", WeatherMetrics(10, 5, 10, 50)),
            CityWeather("Якутск", WeatherMetrics(-50, 10, 0, 0)),
            CityWeather("Саратов", WeatherMetrics(8, 10, 10, 40))
        )
    }

    override suspend fun getCityForecast(cityName: String, duringDays: Int): List<WeatherMetrics> {
        return List(duringDays) { day ->
            WeatherMetrics(
                temperature = 10 + day,
                windSpeed = 5 + day,
                humidity = 80 - day,
                cloudiness = 50 + day * 5
            )
        }
    }

    override suspend fun getCityAverageForecast(cityName: String, duringDays: Int): CityWeather {
        val forecasts = getCityForecast(cityName, duringDays)
        return CityWeather(
            cityName = cityName,
            metrics = WeatherMetrics(
                temperature = forecasts.map { it.temperature!! }.average().toInt(),
                windSpeed = forecasts.map { it.windSpeed!! }.average().toInt(),
                humidity = forecasts.map { it.humidity!! }.average().toInt(),
                cloudiness = forecasts.map { it.cloudiness!! }.average().toInt()
            )
        )
    }
}