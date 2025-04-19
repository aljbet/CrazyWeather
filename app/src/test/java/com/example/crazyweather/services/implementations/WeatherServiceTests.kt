package com.example.crazyweather.services.implementations

import com.example.crazyweather.models.SupportedCities
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.services.interfaces.IWeatherApi
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherServiceTest {

    private lateinit var weatherApi: IWeatherApi
    private lateinit var weatherService: WeatherService

    @Before
    fun setUp() {
        weatherApi = mockk()
        weatherService = WeatherService(weatherApi)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getCityForecast should delegate call to weatherApi`() = runBlocking {
        val expectedForecast = listOf(
            WeatherMetrics(10, 5, 80, 50),
            WeatherMetrics(12, 6, 70, 60)
        )
        coEvery { weatherApi.getCityForecast("Moscow", 2) } returns expectedForecast

        val result = weatherService.getCityForecast("Moscow", 2)

        assertEquals(expectedForecast, result)
    }

    @Test
    fun `getCityAverageForecast should calculate correct averages`() = runBlocking {
        val forecast = listOf(
            WeatherMetrics(10, 5, 80, 50),
            WeatherMetrics(20, 15, 70, 30)
        )
        coEvery { weatherApi.getCityForecast("London", 2) } returns forecast

        val result = weatherService.getCityAverageForecast("London", 2)

        assertEquals("London", result.cityName)
        assertEquals(15, result.metrics.temperature)
        assertEquals(10, result.metrics.windSpeed)
        assertEquals(75, result.metrics.humidity)
        assertEquals(40, result.metrics.cloudiness)
    }

    @Test
    fun `getCitiesAverageWeather should return average for all supported cities`() = runBlocking {
        SupportedCities.cities.forEach { city ->
            val forecast = listOf(
                WeatherMetrics(10, 2, 20, 5),
                WeatherMetrics(12, 4, 20, 5)
            )
            coEvery { weatherApi.getCityForecast(city.name, 2) } returns forecast
        }

        val result = weatherService.getCitiesAverageWeather(2)

        assertEquals(SupportedCities.cities.size, result.size)

        SupportedCities.cities.forEachIndexed { index, city ->
            assertEquals(city.name, result[index].cityName)
            assertEquals(11, result[index].metrics.temperature)
            assertEquals(3, result[index].metrics.windSpeed)
            assertEquals(20, result[index].metrics.humidity)
            assertEquals(5, result[index].metrics.cloudiness)
        }
    }
}