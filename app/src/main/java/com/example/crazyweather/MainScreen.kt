package com.example.crazyweather

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crazyweather.screens.AccountScreen
import com.example.crazyweather.screens.CurrentWeatherScreen
import com.example.crazyweather.screens.HistoryScreen
import com.example.crazyweather.screens.Screen
import com.example.crazyweather.screens.SearchResultScreen
import com.example.crazyweather.screens.SearchScreen
import com.example.crazyweather.viewmodels.HistoryViewModel
import org.koin.java.KoinJavaComponent.inject

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
            composable(Screen.Account.route) { AccountScreen(navController) }
            composable(Screen.CurrentWeather.route) { CurrentWeatherScreen() }
            composable(Screen.History.route) { HistoryScreen(navController) }
            composable(Screen.Search.route) { SearchScreen(navController) }
            composable(Screen.SearchResult.route) { SearchResultScreen(navController) }
        }
    }
}
