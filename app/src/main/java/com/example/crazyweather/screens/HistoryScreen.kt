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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.crazyweather.models.entities.SearchHistoryItem
import com.example.crazyweather.models.vmmodels.HistoryScreenEvent
import com.example.crazyweather.ui.theme.BorderBlue
import com.example.crazyweather.viewmodels.HistoryViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.getKoin

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // Кнопка назад
        Button(onClick = { navController.popBackStack() }) {
            Text("Назад")
        }

        // Заголовок
        Text("История", fontSize = 24.sp)

        // Кнопка очистки
        Button(
            onClick = { viewModel.handleEvent(HistoryScreenEvent.ClearHistory) },
            enabled = !state.isLoading
        ) {
            Text("Очистить историю")
        }

        // Состояние загрузки
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        // Ошибка
        state.error?.let { error ->
            Text("Ошибка: $error", color = MaterialTheme.colorScheme.error)
        }

        // Список истории
        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            items(state.historyItems) { item ->
                HistoryListItem(item)
            }
        }
    }
}

@Composable
fun HistoryListItem(item: SearchHistoryItem) {
    println(item.searchParams)
    Row(
        modifier = Modifier
            .border(2.dp, BorderBlue)
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item.searchParams.temperature?.let {
            Text("${it.toInt()}°C", modifier = Modifier.padding(end = 8.dp))
        }
        item.searchParams.windSpeed?.let {
            Text("${it.toInt()} м/с", modifier = Modifier.padding(end = 8.dp))
        }
        item.searchParams.humidity?.let {
            Text("${it.toInt()}%", modifier = Modifier.padding(end = 8.dp))
        }
        item.searchParams.cloudiness?.let {
            Text("${it.toInt()}%", modifier = Modifier.padding(end = 8.dp))
        }
    }
}