package com.example.crazyweather.viewmodels

import androidx.lifecycle.ViewModel
import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.services.interfaces.ICitySearchService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchViewModel(
    private val citySearchService: ICitySearchService
) : ViewModel() {

    private val _searchParams = MutableStateFlow(WeatherMetrics())
    val searchParams: StateFlow<WeatherMetrics> = _searchParams

    fun updateSearchParams(newParams: WeatherMetrics) {
        _searchParams.value = newParams
    }

    suspend fun searchCities(): List<CityWeather> {
        return citySearchService.findBestMatchingCities(_searchParams.value)
    }
}