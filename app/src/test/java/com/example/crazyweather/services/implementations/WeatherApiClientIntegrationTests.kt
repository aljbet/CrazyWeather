package com.example.crazyweather.services.implementations

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test
import java.util.concurrent.TimeUnit
import kotlin.test.assertTrue

class WeatherApiClientIntegrationTest {

    private val client = WeatherApiClient(
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            engine {
                config {
                    connectTimeout(5, TimeUnit.SECONDS)
                    readTimeout(5, TimeUnit.SECONDS)
                }
            }
        }
    )

    @Test
    fun `should get real weather forecast for Moscow`() = runBlocking {
        val cityName = "Moscow"
        val days = 2

        val result = client.getCityForecast(cityName, days)

        assertTrue(result.isNotEmpty(), "Forecast should not be empty")
        assertTrue(result.size >= 24 * days, "Should return at least 24 hours per day")

        result.forEach { metrics ->
            assertTrue(metrics.temperature in -50..50, "Temperature should be reasonable")
            assertTrue(metrics.windSpeed in 0..200, "Wind speed should be reasonable")
            assertTrue(metrics.humidity in 0..100, "Humidity should be 0-100%")
            assertTrue(metrics.cloudiness in 0..100, "Cloudiness should be 0-100%")
        }
    }

    @Test
    fun `should handle different city names`() = runBlocking {
        val cities = listOf("London", "Paris", "Berlin")
        val days = 1

        cities.forEach { city ->
            val result = client.getCityForecast(city, days)

            assertTrue(result.isNotEmpty(), "Forecast for $city should not be empty")
            assertTrue(result.all { it.temperature != 0 }, "Temperature should not be zero")
        }
    }
}