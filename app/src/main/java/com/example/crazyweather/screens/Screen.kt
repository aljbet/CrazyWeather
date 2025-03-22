package com.example.crazyweather.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    object Account : Screen("account", Icons.Default.AccountCircle, "Аккаунт")
    object CurrentWeather : Screen("current_weather", Icons.Default.LocationOn, "Погода")
    object History : Screen("history", Icons.Default.AccountCircle, "История")
    object Search : Screen("search", Icons.Default.Search, "Поиск")
    object SearchResult : Screen("search_result", Icons.Default.Search, "Результат поиска")
}
