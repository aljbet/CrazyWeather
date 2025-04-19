package com.example.crazyweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.crazyweather.ui.theme.CrazyWeatherTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrazyWeatherTheme {
                MainScreen()
            }
        }

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule, ktorModule, dbModule)
        }
    }
}