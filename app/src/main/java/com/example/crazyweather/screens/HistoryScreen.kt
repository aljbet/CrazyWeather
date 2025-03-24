package com.example.crazyweather.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.crazyweather.ui.theme.BorderBlue

@Composable
fun HistoryScreen(navController: NavController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
    ) {
        Button(onClick = {
            navController.navigate(Screen.Account.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад"
            )
        }

        Text(text = "История", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        HistoryListItem(25, 10, 75, 100)
        Spacer(modifier = Modifier.height(8.dp))
        HistoryListItem(5, 20, 20, 100)
        Spacer(modifier = Modifier.height(8.dp))
        HistoryListItem(25, 10, 75, 100)
    }
}

@Composable
fun HistoryListItem(temperature: Int, wind: Int, humidity: Int, cloudiness: Int) {
    Row(
        modifier = Modifier
            .border(2.dp, BorderBlue)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(temperature.toString() + "C\u00B0", Modifier.padding(10.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(wind.toString() + "м/с", Modifier.padding(10.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("$humidity%", Modifier.padding(10.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("$cloudiness%", Modifier.padding(10.dp))
    }
}