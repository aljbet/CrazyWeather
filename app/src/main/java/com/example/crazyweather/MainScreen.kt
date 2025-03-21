package com.example.crazyweather

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationPanel(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.CurrentWeather.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Search.route) { SearchScreen() }
            composable(Screen.CurrentWeather.route) { CurrentWeatherScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}
