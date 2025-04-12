package com.example.crazyweather.services.implementations

import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.services.interfaces.IWeatherApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

class WeatherApiClient (
    private val ktorClient: HttpClient
) : IWeatherApi {
    private val baseUrl = "https://api.weatherapi.com/v1/"
    private val apiKey = "29aef7d8ddae46f8b33132327253003"

    override suspend fun getCityForecast(cityName: String, duringDays: Int): List<WeatherMetrics> {
        val response = ktorClient.get("${baseUrl}forecast.json") {
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
                    temperature = hour.temp_c.toInt(),
                    windSpeed = hour.wind_kph.toInt(),
                    humidity = hour.humidity.toInt(),
                    cloudiness = hour.cloud.toInt()
                )
            }
        }
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