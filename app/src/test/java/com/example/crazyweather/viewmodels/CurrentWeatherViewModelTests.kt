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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrentWeatherViewModelTests {

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
    fun `loadWeather should return Success`() = runTest {
        val testCity = "Moscow"
        val testWeather = WeatherMetrics(20, 10, 50, 30)
        coEvery { mockWeatherService.getCityForecast(testCity, 1) } returns listOf(testWeather)

        viewModel.loadWeather(testCity)
        advanceUntilIdle()

        val state = viewModel.weatherState.value as CurrentWeatherState.Success
        assertEquals(testCity, state.cityName)
        assertEquals(testWeather, state.currentWeather)
    }

}