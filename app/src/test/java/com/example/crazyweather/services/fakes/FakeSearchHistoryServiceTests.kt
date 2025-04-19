package com.example.crazyweather.services.fakes

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FakeSearchHistoryServiceTest {

    private val service = FakeSearchHistoryService()

    @Test
    fun `initial history should contain 2 items`() = runBlocking {
        val history = service.getSearchHistory()

        assertEquals(2, history.size)
    }

    @Test
    fun `history should be sorted by timestamp descending`() = runBlocking {
        val history = service.getSearchHistory()

        assertTrue(history[0].timestamp > history[1].timestamp)
    }

    @Test
    fun `saveSearch should add new item to history`() = runBlocking {
        val initialCount = service.getSearchHistory().size
        val testMetrics = WeatherMetrics(15, 5, 50, 30)

        service.saveSearch(testMetrics, null)
        val updatedHistory = service.getSearchHistory()

        assertEquals(initialCount + 1, updatedHistory.size)
        assertEquals(testMetrics, updatedHistory[0].searchParams)
        assertNotNull(updatedHistory[0].id)
        assertTrue(updatedHistory[0].timestamp > 0)
    }

    @Test
    fun `saveSearch with results should not affect basic functionality`() = runBlocking {
        val testMetrics = WeatherMetrics(20, 10, 60, 40)
        val testResults = listOf(
            CityWeather("Moscow", WeatherMetrics(18, 8, 55, 45)),
            CityWeather("SPb", WeatherMetrics(16, 9, 50, 50)))

        service.saveSearch(testMetrics, testResults)
        val history = service.getSearchHistory()

        assertEquals(testMetrics, history[0].searchParams)
    }

    @Test
    fun `clearHistory should remove all items and reset ID counter`() = runBlocking {
        val initialHistory = service.getSearchHistory()
        assertTrue(initialHistory.isNotEmpty())

        service.clearHistory()
        val clearedHistory = service.getSearchHistory()

        assertTrue(clearedHistory.isEmpty())

        service.saveSearch(WeatherMetrics(10, 5, 50, 50), null)
        val newItem = service.getSearchHistory().first()
        assertEquals(1L, newItem.id)
    }

    @Test
    fun `new items should have incrementing IDs`() = runBlocking {
        val initialLastId = service.getSearchHistory().maxOf { it.id }

        service.saveSearch(WeatherMetrics(1, 1, 1, 1), null)
        service.saveSearch(WeatherMetrics(2, 2, 2, 2), null)
        val history = service.getSearchHistory()

        assertEquals(initialLastId + 1, history[0].id)
        assertEquals(initialLastId + 2, history[1].id)
    }
}