package com.example.crazyweather.viewmodels

import com.example.crazyweather.models.entities.SearchHistoryItem
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.models.vmmodels.HistoryScreenEvent
import com.example.crazyweather.services.interfaces.ISearchHistoryService
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var mockSearchHistoryService: ISearchHistoryService
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockSearchHistoryService = mockk(relaxed = true)
        viewModel = HistoryViewModel(mockSearchHistoryService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `initial load should fetch history`() = runTest {
        val mockHistory = listOf(
            SearchHistoryItem(1, WeatherMetrics(), 0),
            SearchHistoryItem(2, WeatherMetrics(), 1)
        )
        coEvery { mockSearchHistoryService.getSearchHistory() } returns mockHistory

        advanceUntilIdle()

        assertFalse(viewModel.state.value.isLoading)
        assertNull(viewModel.state.value.error)
    }

    @Test
    fun `loadHistory should update state correctly`() = runTest {
        val mockHistory = listOf(
            SearchHistoryItem(1, WeatherMetrics(), 0),
            SearchHistoryItem(2, WeatherMetrics(), 1)
        )

        coEvery { mockSearchHistoryService.getSearchHistory() } returns mockHistory

        viewModel.handleEvent(HistoryScreenEvent.LoadHistory)
        advanceUntilIdle()

        assertEquals(mockHistory, viewModel.state.value.historyItems)
        assertFalse(viewModel.state.value.isLoading)
        assertNull(viewModel.state.value.error)
    }

    @Test
    fun `clearHistory should empty the history`() = runTest {
        coEvery { mockSearchHistoryService.clearHistory() } returns Unit
        coEvery { mockSearchHistoryService.getSearchHistory() } returns emptyList()

        viewModel.handleEvent(HistoryScreenEvent.ClearHistory)
        advanceUntilIdle()

        assertTrue(viewModel.state.value.historyItems.isEmpty())
    }

    @Test
    fun `navigateBack event should not modify state`() = runTest {
        val initialState = viewModel.state.value

        viewModel.handleEvent(HistoryScreenEvent.NavigateBack)
        advanceUntilIdle()

        assertEquals(initialState, viewModel.state.value)
    }
}