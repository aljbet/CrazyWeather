package com.example.crazyweather

import com.example.crazyweather.services.fakes.FakeSearchHistoryService
import com.example.crazyweather.services.implementations.CitySearchService
import com.example.crazyweather.services.implementations.WeatherApiClient
import com.example.crazyweather.services.implementations.WeatherService
import com.example.crazyweather.services.interfaces.ICitySearchService
import com.example.crazyweather.services.interfaces.ISearchHistoryService
import com.example.crazyweather.services.interfaces.IWeatherApi
import com.example.crazyweather.services.interfaces.IWeatherService
import com.example.crazyweather.viewmodels.CurrentWeatherViewModel
import com.example.crazyweather.viewmodels.HistoryViewModel
import com.example.crazyweather.viewmodels.SearchResultViewModel
import com.example.crazyweather.viewmodels.SearchViewModel
import com.example.crazyweather.viewmodels.SharedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.engine.cio.*

val appModule = module {
    single<IWeatherService> { WeatherService(get()) }
    single<IWeatherApi> { WeatherApiClient(get()) }
    single<ISearchHistoryService> { FakeSearchHistoryService() }
    single<ICitySearchService> { CitySearchService(get()) }

    viewModel { HistoryViewModel(get()) }
    viewModel { SharedViewModel() }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { SearchResultViewModel(get()) }
    viewModel { CurrentWeatherViewModel(get()) }
}

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        })
    }

    engine {
        maxConnectionsCount = 1000
        endpoint {
            maxConnectionsPerRoute = 100
            pipelineMaxSize = 20
            keepAliveTime = 5000
            connectTimeout = 5000
            connectAttempts = 5
        }
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 15000
        connectTimeoutMillis = 15000
        socketTimeoutMillis = 15000
    }
}

val ktorModule = module {
    single { client }
}