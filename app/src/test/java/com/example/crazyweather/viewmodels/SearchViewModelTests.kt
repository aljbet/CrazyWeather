package com.example.crazyweather.viewmodels

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.services.interfaces.ICitySearchService
import com.example.crazyweather.services.interfaces.ISearchHistoryService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTests {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var mockCitySearchService: ICitySearchService
    private lateinit var mockSearchHistoryService: ISearchHistoryService
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockCitySearchService = mockk()
        mockSearchHistoryService = mockk()
        viewModel = SearchViewModel(mockCitySearchService, mockSearchHistoryService)
    }

    @Test
    fun `updateSearchParams should update flow value`() = runTest {
        val testParams = WeatherMetrics(
            temperature = 20,
            windSpeed = 10,
            humidity = 50,
            cloudiness = 30
        )

        viewModel.updateSearchParams(testParams)

        assertEquals(testParams, viewModel.searchParams.value)
    }

    @Test
    fun `searchCities should return matching cities and save history`() = runTest {
        val testParams = WeatherMetrics(temperature = 15)
        val testCities = listOf(
            CityWeather("Moscow", WeatherMetrics(18, 8, 55, 40)),
        )

        viewModel.updateSearchParams(testParams)
        coEvery { mockCitySearchService.findBestMatchingCities(testParams) } returns testCities
        coEvery { mockSearchHistoryService.saveSearch(testParams, testCities) } returns Unit

        val result = viewModel.searchCities()

        assertEquals(testCities, result)
        coVerify {
            mockCitySearchService.findBestMatchingCities(testParams)
            mockSearchHistoryService.saveSearch(testParams, testCities)
        }
    }
}