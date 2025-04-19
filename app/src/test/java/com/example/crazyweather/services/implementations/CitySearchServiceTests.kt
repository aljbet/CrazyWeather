package com.example.crazyweather.services.implementations

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CitySearchServiceTests {
    @MockK
    private lateinit var mockWeatherService: WeatherService

    private lateinit var service: CitySearchService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        service = CitySearchService(mockWeatherService)
    }
    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `findBestMatchingCities should return sorted cities`() = runBlocking {
        val userMetrics = WeatherMetrics(20, 10, 50, 30)
        val mockCities = listOf(
            CityWeather("Moscow", WeatherMetrics(25, 15, 60, 40)),
            CityWeather("Saratov", WeatherMetrics(18, 8, 45, 25)),
            CityWeather("Yakutsk", WeatherMetrics(15, 5, 30, 10))
        )

        coEvery { mockWeatherService.getCitiesAverageWeather(7) } returns mockCities

        val result = service.findBestMatchingCities(userMetrics)

        assertEquals(3, result.size)
        assertTrue(result[0].matchPercentage > result[1].matchPercentage)
        assertTrue(result[1].matchPercentage > result[2].matchPercentage)
    }

    @Test
    fun `getCityForecast should return correct results`() = runBlocking {
        val expectedForecast = listOf(
            WeatherMetrics(15, 5, 70, 40),
            WeatherMetrics(16, 6, 75, 45)
        )
        coEvery { mockWeatherService.getCityForecast("Moscow", 2) } returns expectedForecast

        val result = service.getCityForecast("Moscow", 2)

        assertEquals(expectedForecast, result)
    }
}