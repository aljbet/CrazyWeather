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
import com.example.crazyweather.ui.theme.BorderBlue

@Composable
fun SearchScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
    ) {
        Text("Введите параметры:", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        SearchItem("Температура", "C\u00B0")
        Spacer(modifier = Modifier.height(8.dp))
        SearchItem("Сила ветра", "м/с")
        Spacer(modifier = Modifier.height(8.dp))
        SearchItem("Влажность", "%")
        Spacer(modifier = Modifier.height(8.dp))
        SearchItem("Облачность", "%")
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            navController.navigate(Screen.SearchResult.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Text("Найти подходящий город")
        }
    }
}

@Composable
fun SearchItem(title: String, measurement: String) {
    var userInput by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .border(2.dp, BorderBlue)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, Modifier
            .padding(5.dp)
            .width(200.dp))
        Spacer(modifier = Modifier.width(8.dp))
        TextField(
            value = userInput,
            onValueChange = { userInput = it },
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
    SearchScreen(rememberNavController())
}

@Preview
@Composable
fun SearchItemPreview() {
    SearchItem("Саратов", "%")
}
