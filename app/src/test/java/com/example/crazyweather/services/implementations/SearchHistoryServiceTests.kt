package com.example.crazyweather.services.implementations

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.SearchHistoryItem
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.repository.HistoryDao
import com.example.crazyweather.repository.HistoryItemTable
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchHistoryServiceTest {

    @MockK
    private lateinit var mockHistoryDao: HistoryDao

    private lateinit var service: SearchHistoryService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        service = SearchHistoryService(mockHistoryDao)
    }

    @Test
    fun `saveSearch should insert item with correct parameters and increment ID`() = runBlocking {
        val testMetrics = WeatherMetrics(20, 10, 50, 30)
        val testResults = listOf(CityWeather("Moscow", WeatherMetrics(18, 8, 55, 40)))
        val captureSlot = slot<HistoryItemTable>()

        coEvery { mockHistoryDao.insert(capture(captureSlot)) } just Runs

        service.saveSearch(testMetrics, testResults)

        coVerify { mockHistoryDao.insert(any()) }
        val insertedItem = captureSlot.captured
        assertEquals(1L, insertedItem.id)
        assertEquals(20, insertedItem.temperature)
        assertEquals(10, insertedItem.windSpeed)
        assertEquals(50, insertedItem.humidity)
        assertEquals(30, insertedItem.cloudiness)
        assertTrue(insertedItem.timestamp > 0)
    }

    @Test
    fun `clearHistory should delete all items and reset ID counter`() = runBlocking {
        coEvery { mockHistoryDao.deleteAll() } just Runs

        service.clearHistory()

        coVerify { mockHistoryDao.deleteAll() }

        val testMetrics = WeatherMetrics(10, 5, 50, 50)
        val captureSlot = slot<HistoryItemTable>()
        coEvery { mockHistoryDao.insert(capture(captureSlot)) } just Runs

        service.saveSearch(testMetrics, null)
        assertEquals(1L, captureSlot.captured.id)
    }

    @Test
    fun `saveSearch should handle null values in WeatherMetrics`() = runBlocking {
        val testMetrics = WeatherMetrics()
        val captureSlot = slot<HistoryItemTable>()
        coEvery { mockHistoryDao.insert(capture(captureSlot)) } just Runs

        service.saveSearch(testMetrics, null)

        val insertedItem = captureSlot.captured
        assertEquals(0, insertedItem.temperature)
        assertEquals(0, insertedItem.windSpeed)
        assertEquals(0, insertedItem.humidity)
        assertEquals(0, insertedItem.cloudiness)
    }

    @Test
    fun `getSearchHistory should return empty list when no items`() = runBlocking {
        coEvery { mockHistoryDao.getAll() } returns emptyList()

        val result = service.getSearchHistory()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `saveSearch should increment ID for each new item`() = runBlocking {
        val captureSlot = mutableListOf<HistoryItemTable>()
        coEvery { mockHistoryDao.insert(capture(captureSlot)) } just Runs
        val testMetrics = WeatherMetrics(10, 5, 50, 50)

        service.saveSearch(testMetrics, null)
        service.saveSearch(testMetrics, null)
        service.saveSearch(testMetrics, null)

        assertEquals(3, captureSlot.size)
        assertEquals(1L, captureSlot[0].id)
        assertEquals(2L, captureSlot[1].id)
        assertEquals(3L, captureSlot[2].id)
    }
}