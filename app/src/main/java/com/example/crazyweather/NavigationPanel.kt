package com.example.crazyweather

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.crazyweather.screens.Screen
import com.example.crazyweather.ui.theme.AviasalesBlue

@Composable
fun NavigationPanel(navController: NavController) {
    BottomNavigation(
        backgroundColor = AviasalesBlue
    ) {
        val items = listOf(Screen.Search, Screen.CurrentWeather, Screen.Account)
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = screen.title,
                    modifier = Modifier.padding(10.dp)) },
                label = { Text(screen.title, textAlign = TextAlign.Center, color = Color.White) },
                selected = currentRoute == screen.route,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.White,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}