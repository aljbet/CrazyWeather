package com.example.crazyweather

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    object Search : Screen("search", Icons.Default.Search, "Поиск")
    object CurrentWeather : Screen("current_weather", Icons.Default.LocationOn, "Погода")
    object Settings : Screen("settings", Icons.Default.Settings, "Настройки")
}
