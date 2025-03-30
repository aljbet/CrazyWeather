package com.example.crazyweather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.services.interfaces.ICitySearchService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchResultViewModel(
    private val citySearchService: ICitySearchService
) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<CityWeather>>(emptyList())
    val searchResults: StateFlow<List<CityWeather>> = _searchResults

    private val _cityForecast = MutableStateFlow<List<WeatherMetrics>>(emptyList())
    val cityForecast: StateFlow<List<WeatherMetrics>> = _cityForecast

    fun loadSearchResults(results: List<CityWeather>) {
        _searchResults.value = results
    }

    fun loadCityForecast(cityName: String, days: Int = 5) {
        viewModelScope.launch {
            _cityForecast.value = citySearchService.getCityForecast(cityName, days)
        }
    }
}