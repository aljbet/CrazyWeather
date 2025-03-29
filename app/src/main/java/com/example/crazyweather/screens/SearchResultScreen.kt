package com.example.crazyweather.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.crazyweather.ui.theme.BorderBlue
import com.example.crazyweather.viewmodels.SearchResultViewModel
import com.example.crazyweather.viewmodels.SharedViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchResultScreen(
    navController: NavController,
    viewModel: SearchResultViewModel = koinViewModel(),
    sharedViewModel: SharedViewModel = koinViewModel()
) {
    val searchResults by sharedViewModel.searchResults.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Назад")
        }

        LazyColumn {
            items(searchResults) { city ->
                CityListItem(
                    name = city.cityName,
                    percent = city.matchPercentage,
                    onClick = { viewModel.loadCityForecast(city.cityName) }
                )
            }
        }
    }
}

@Composable
fun CityListItem(
    name: String,
    percent: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .border(2.dp, BorderBlue)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, Modifier.weight(1f))
        Text("$percent%")
    }
}