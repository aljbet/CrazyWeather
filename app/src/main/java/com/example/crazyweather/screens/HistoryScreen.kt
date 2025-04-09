package com.example.crazyweather.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crazyweather.appModule
import com.example.crazyweather.models.entities.SearchHistoryItem
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.models.vmmodels.HistoryScreenEvent
import com.example.crazyweather.ui.theme.BorderBlue
import com.example.crazyweather.viewmodels.HistoryViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

@Composable
fun HistoryScreen(
    navController: NavController
) {
    val historyViewModel: HistoryViewModel = koinViewModel<HistoryViewModel>()
    val state by historyViewModel.state.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // Кнопка назад
        Button(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад"
            )
        }

        // Заголовок
        Text("История", fontSize = 24.sp)

        // Кнопка очистки
        Button(
            onClick = { historyViewModel.handleEvent(HistoryScreenEvent.ClearHistory) },
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
                Spacer(modifier = Modifier.height(8.dp))
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
            Text("${it.toInt()}°C", modifier = Modifier
                .padding(10.dp)
                .width(50.dp))
        }
        item.searchParams.windSpeed?.let {
            Text("${it.toInt()} м/с", modifier = Modifier
                .padding(10.dp)
                .width(70.dp))
        }
        item.searchParams.humidity?.let {
            Text("${it.toInt()}%", modifier = Modifier
                .padding(10.dp)
                .width(50.dp))
        }
        item.searchParams.cloudiness?.let {
            Text("${it.toInt()}%", modifier = Modifier
                .padding(10.dp)
                .width(50.dp))
        }
    }
}

@Preview
@Composable
fun HistoryScreenPreview() {
    startKoin {
        modules(appModule)
    }
    HistoryScreen(rememberNavController())
}

@Preview
@Composable
fun HistoryListItemPreview() {
    HistoryListItem(
        SearchHistoryItem(
            5, WeatherMetrics(1.0, 1.0, 1.0, 1.0),
            timestamp = System.currentTimeMillis(),
            results = null,
        )
    )
}