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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crazyweather.appModule
import com.example.crazyweather.ui.theme.BorderBlue
import com.example.crazyweather.viewmodels.SearchViewModel
import com.example.crazyweather.viewmodels.SharedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

@Composable
fun SearchScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    val searchViewModel: SearchViewModel = koinViewModel<SearchViewModel>()
    val searchParams by searchViewModel.searchParams.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Введите параметры:", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        SearchItem(
            title = "Температура",
            measurement = "C°",
            value = searchParams.temperature?.toString() ?: "",
            onValueChange = { value ->
                searchViewModel.updateSearchParams(
                    searchParams.copy(temperature = value.toDoubleOrNull())
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        SearchItem(
            title = "Сила ветра",
            measurement = "м/с",
            value = searchParams.windSpeed?.toString() ?: "",
            onValueChange = { value ->
                searchViewModel.updateSearchParams(
                    searchParams.copy(windSpeed = value.toDoubleOrNull())
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        SearchItem(
            title = "Влажность",
            measurement = "%",
            value = searchParams.humidity?.toString() ?: "",
            onValueChange = { value ->
                searchViewModel.updateSearchParams(
                    searchParams.copy(humidity = value.toDoubleOrNull())
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        SearchItem(
            title = "Облачность",
            measurement = "%",
            value = searchParams.cloudiness?.toString() ?: "",
            onValueChange = { value ->
                searchViewModel.updateSearchParams(
                    searchParams.copy(cloudiness = value.toDoubleOrNull())
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    val results = searchViewModel.searchCities()
                    sharedViewModel.setSearchResults(results)
                    navController.navigate(Screen.SearchResult.route)
                }
            }
        ) {
            Text("Найти подходящий город")
        }
    }
}

@Composable
fun SearchItem(
    title: String,
    measurement: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .border(2.dp, BorderBlue)
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title, Modifier
                .padding(5.dp)
                .width(150.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier.width(60.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(measurement, Modifier.padding(5.dp))
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    startKoin {
        modules(appModule)
    }
    SearchScreen(
        rememberNavController(), koinViewModel<SharedViewModel>()
    )
}

@Preview
@Composable
fun SearchItemPreview() {
    SearchItem(
        title = "Сила ветра",
        measurement = "м/с",
        value = "15",
        onValueChange = {})
}
