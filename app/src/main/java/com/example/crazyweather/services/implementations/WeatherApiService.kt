package com.example.crazyweather.services.implementations

import com.example.crazyweather.models.SupportedCities
import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.services.interfaces.IWeatherApiService
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

class WeatherApiService : IWeatherApiService {
    private val baseUrl = "https://api.weatherapi.com/v1/"
    private val apiKey = "29aef7d8ddae46f8b33132327253003"

    override suspend fun getCitiesAverageWeather(duringDays: Int): List<CityWeather> {
        return SupportedCities.cities.map { city ->
            getCityAverageForecast(city.name, duringDays)
        }
    }

    override suspend fun getCityForecast(cityName: String, duringDays: Int): List<WeatherMetrics> {
        val response = ApiClient.client.get("${baseUrl}forecast.json") {
            url {
                parameters.apply {
                    append("q", cityName)
                    append("days", duringDays.toString())
                    append("key", apiKey)
                }
            }
        }.body<WeatherApiResponse>()

        return response.forecast.forecastday.flatMap { forecastDay ->
            forecastDay.hour.map { hour ->
                WeatherMetrics(
                    temperature = hour.temp_c,
                    windSpeed = hour.wind_kph,
                    humidity = hour.humidity,
                    cloudiness = hour.cloud
                )
            }
        }
    }

    override suspend fun getCityAverageForecast(cityName: String, duringDays: Int): CityWeather {
        val forecast = getCityForecast(cityName, duringDays)

        return CityWeather(
            cityName = cityName,
            metrics = calculateAverageMetrics(forecast)
        )
    }

    private fun calculateAverageMetrics(metrics: List<WeatherMetrics>): WeatherMetrics {
        return WeatherMetrics(
            temperature = metrics.mapNotNull { it.temperature }.average(),
            windSpeed = metrics.mapNotNull { it.windSpeed }.average(),
            humidity = metrics.mapNotNull { it.humidity }.average(),
            cloudiness = metrics.mapNotNull { it.cloudiness }.average()
        )
    }

    @Serializable
    private data class WeatherApiResponse(
        val forecast: Forecast
    )

    @Serializable
    private data class Forecast(
        val forecastday: List<ForecastDay>
    )

    @Serializable
    private data class ForecastDay(
        val hour: List<Hour>
    )

    @Serializable
    private data class Hour(
        val temp_c: Double,
        val wind_kph: Double,
        val humidity: Double,
        val cloud: Double
    )
}