package com.example.crazyweather.viewmodels

import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.models.vmmodels.CurrentWeatherState
import com.example.crazyweather.services.interfaces.IWeatherService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrentWeatherViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var mockWeatherService: IWeatherService
    private lateinit var viewModel: CurrentWeatherViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockWeatherService = mockk()
        viewModel = CurrentWeatherViewModel(mockWeatherService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadWeather should emit Success with weather data`() = runTest {
        val testCity = "Moscow"
        val testWeather = WeatherMetrics(20, 10, 50, 30)
        coEvery { mockWeatherService.getCityForecast(testCity, 1) } returns listOf(testWeather)

        viewModel.loadWeather(testCity)
        advanceUntilIdle()

        val state = viewModel.weatherState.value as CurrentWeatherState.Success
        assertEquals(testCity, state.cityName)
        assertEquals(testWeather, state.currentWeather)
    }

    @Test
    fun `loadWeather should handle multiple calls correctly`() = runTest {
        val city1 = "Moscow"
        val weather1 = WeatherMetrics(20, 10, 50, 30)
        val city2 = "London"
        val weather2 = WeatherMetrics(15, 12, 70, 80)

        coEvery { mockWeatherService.getCityForecast(city1, 1) } returns listOf(weather1)
        coEvery { mockWeatherService.getCityForecast(city2, 1) } returns listOf(weather2)

        viewModel.loadWeather(city1)
        advanceUntilIdle()
        val state1 = viewModel.weatherState.value as CurrentWeatherState.Success

        viewModel.loadWeather(city2)
        advanceUntilIdle()
        val state2 = viewModel.weatherState.value as CurrentWeatherState.Success

        assertEquals(city1, state1.cityName)
        assertEquals(weather1, state1.currentWeather)
        assertEquals(city2, state2.cityName)
        assertEquals(weather2, state2.currentWeather)
    }
}