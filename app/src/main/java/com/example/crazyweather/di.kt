package com.example.crazyweather

import com.example.crazyweather.services.fakes.FakeSearchHistoryService
import com.example.crazyweather.services.fakes.FakeWeatherApiService
import com.example.crazyweather.services.implementations.CitySearchService
import com.example.crazyweather.services.interfaces.ICitySearchService
import com.example.crazyweather.services.interfaces.ISearchHistoryService
import com.example.crazyweather.services.interfaces.IWeatherApiService
import com.example.crazyweather.viewmodels.CurrentWeatherViewModel
import com.example.crazyweather.viewmodels.HistoryViewModel
import com.example.crazyweather.viewmodels.SearchResultViewModel
import com.example.crazyweather.viewmodels.SearchViewModel
import com.example.crazyweather.viewmodels.SharedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IWeatherApiService> { FakeWeatherApiService() }
    single<ISearchHistoryService> { FakeSearchHistoryService() }
    single<ICitySearchService> { CitySearchService(get()) }

    viewModel { HistoryViewModel(get()) }
    viewModel { SharedViewModel() }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { SearchResultViewModel(get()) }
    viewModel { CurrentWeatherViewModel(get()) }
}