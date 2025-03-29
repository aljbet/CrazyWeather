package com.example.crazyweather.viewmodels

import androidx.lifecycle.ViewModel
import com.example.crazyweather.models.entities.CityWeather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {
    private val _searchResults = MutableStateFlow<List<CityWeather>>(emptyList())
    val searchResults: StateFlow<List<CityWeather>> = _searchResults

    fun setSearchResults(results: List<CityWeather>) {
        _searchResults.value = results
    }
}