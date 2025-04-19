package com.example.crazyweather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.models.vmmodels.CurrentWeatherState
import com.example.crazyweather.services.interfaces.IWeatherService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrentWeatherViewModel(
    private val weatherService: IWeatherService,
) : ViewModel() {

    private val _weatherState = MutableStateFlow<CurrentWeatherState>(CurrentWeatherState.Loading)
    val weatherState: StateFlow<CurrentWeatherState> = _weatherState

    fun loadWeather(cityName: String) {
        viewModelScope.launch {
            _weatherState.value = CurrentWeatherState.Loading
            try {
                val currentWeather = weatherService.getCityForecast(cityName, 1).firstOrNull()

                _weatherState.value = CurrentWeatherState.Success(
                    cityName = cityName,
                    currentWeather = currentWeather ?: WeatherMetrics()
                )
            } catch (e: Exception) {
                _weatherState.value = CurrentWeatherState.Error(e.message ?: "Unknown error")
            }
        }
    }
}