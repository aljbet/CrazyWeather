package com.example.crazyweather.models.vmmodels

sealed interface HistoryScreenEvent {
    object LoadHistory : HistoryScreenEvent
    object ClearHistory : HistoryScreenEvent
    object NavigateBack : HistoryScreenEvent
}