package com.example.crazyweather.models.vmmodels

import com.example.crazyweather.models.entities.SearchHistoryItem

data class HistoryScreenState(
    val historyItems: List<SearchHistoryItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)