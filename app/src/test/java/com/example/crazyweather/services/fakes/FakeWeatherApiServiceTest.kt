package com.example.crazyweather.services.fakes

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class FakeWeatherApiServiceTest {

    private val service = FakeWeatherApiService()

    @Test
    fun `getCitiesAverageWeather should return fixed list of cities`() = runBlocking {
        val result = service.getCitiesAverageWeather(3)

        val expected = listOf(
            CityWeather("Сургут", WeatherMetrics(10, 5, 10, 50)),
            CityWeather("Якутск", WeatherMetrics(-50, 10, 0, 0)),
            CityWeather("Саратов", WeatherMetrics(8, 10, 10, 40)))

        assertEquals(expected, result)
    }

    @Test
    fun `getCityForecast should return forecast with incrementing values`() = runBlocking {
        val days = 3

        val result = service.getCityForecast("TestCity", days)

        assertEquals(days, result.size)

        val expected = listOf(
            WeatherMetrics(10, 5, 80, 50),
            WeatherMetrics(11, 6, 79, 55),
            WeatherMetrics(12, 7, 78, 60)
        )

        assertEquals(expected, result)
    }

    @Test
    fun `getCityForecast with 1 day should return single forecast`() = runBlocking {
        val result = service.getCityForecast("TestCity", 1)

        assertEquals(1, result.size)
        assertEquals(WeatherMetrics(10, 5, 80, 50), result[0])
    }

    @Test
    fun `getCityAverageForecast should calculate correct averages`() = runBlocking {
        val cityName = "Москва"
        val days = 3

        val result = service.getCityAverageForecast(cityName, days)

        val expectedMetrics = WeatherMetrics(
            temperature = (10 + 11 + 12) / 3,
            windSpeed = (5 + 6 + 7) / 3,
            humidity = (80 + 79 + 78) / 3,
            cloudiness = (50 + 55 + 60) / 3
        )

        assertEquals(cityName, result.cityName)
        assertEquals(expectedMetrics, result.metrics)
    }

    @Test
    fun `getCityAverageForecast with single day should return same values`() = runBlocking {
        val result = service.getCityAverageForecast("Москва", 1)

        assertEquals(WeatherMetrics(10, 5, 80, 50), result.metrics)
    }
}