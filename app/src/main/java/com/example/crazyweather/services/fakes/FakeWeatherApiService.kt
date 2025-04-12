package com.example.crazyweather.services.fakes

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.services.interfaces.IWeatherService

class FakeWeatherApiService : IWeatherService {
    override suspend fun getCitiesAverageWeather(duringDays: Int): List<CityWeather> {
        return listOf(
            CityWeather("Сургут", WeatherMetrics(10.0, 5.0, 10.0, 50.0)),
            CityWeather("Якутск", WeatherMetrics(-50.0, 10.0, 0.0, 0.0)),
            CityWeather("Саратов", WeatherMetrics(8.0, 10.0, 10.0, 40.0))
        )
    }

    override suspend fun getCityForecast(cityName: String, duringDays: Int): List<WeatherMetrics> {
        return List(duringDays) { day ->
            WeatherMetrics(
                temperature = 10.0 + day,
                windSpeed = 5.0 + day,
                humidity = 80.0 - day,
                cloudiness = 50.0 + day * 5
            )
        }
    }

    override suspend fun getCityAverageForecast(cityName: String, duringDays: Int): CityWeather {
        val forecasts = getCityForecast(cityName, duringDays)
        return CityWeather(
            cityName = cityName,
            metrics = WeatherMetrics(
                temperature = forecasts.map { it.temperature!! }.average(),
                windSpeed = forecasts.map { it.windSpeed!! }.average(),
                humidity = forecasts.map { it.humidity!! }.average(),
                cloudiness = forecasts.map { it.cloudiness!! }.average()
            )
        )
    }
}