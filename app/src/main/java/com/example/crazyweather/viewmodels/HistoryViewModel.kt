package com.example.crazyweather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crazyweather.models.vmmodels.HistoryScreenEvent
import com.example.crazyweather.models.vmmodels.HistoryScreenState
import com.example.crazyweather.services.interfaces.ISearchHistoryService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val searchHistoryService: ISearchHistoryService
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryScreenState())
    val state: StateFlow<HistoryScreenState> = _state.asStateFlow()

    init {
        loadHistory()
    }

    fun handleEvent(event: HistoryScreenEvent) {
        when (event) {
            HistoryScreenEvent.LoadHistory -> loadHistory()
            HistoryScreenEvent.ClearHistory -> clearHistory()
            HistoryScreenEvent.NavigateBack -> {}
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val history = searchHistoryService.getSearchHistory()
                _state.update { it.copy(historyItems = history, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    private fun clearHistory() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                searchHistoryService.clearHistory()
                _state.update { it.copy(historyItems = emptyList(), isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}