package com.example.crazyweather

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
import androidx.compose.material.icons.filled.ArrowBack
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
fun SearchResultScreen(navController: NavController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
    ) {
        Button(onClick = {
            navController.navigate(Screen.Search.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Назад"
            )
        }

        Text(text = "Результат поиска", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        CityListItem("Сургут", 100)
        Spacer(modifier = Modifier.height(8.dp))
        CityListItem("Якутск", 100)
        Spacer(modifier = Modifier.height(8.dp))
        CityListItem("Саратов", 100)

    }
}

@Composable
fun CityListItem(name: String, percent: Int) {
    Row(
        modifier = Modifier
            .border(2.dp, BorderBlue)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, Modifier
            .weight(1f)
            .padding(10.dp), fontSize = 20.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(percent.toString() + "%", Modifier.padding(10.dp))
    }
}