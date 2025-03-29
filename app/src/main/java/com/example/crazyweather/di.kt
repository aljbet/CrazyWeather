package com.example.crazyweather

import com.example.crazyweather.services.fakes.FakeSearchHistoryService
import com.example.crazyweather.services.fakes.FakeWeatherApiService
import com.example.crazyweather.services.implementations.CitySearchService
import com.example.crazyweather.services.implementations.SearchHistoryService
import com.example.crazyweather.services.interfaces.ICitySearchService
import com.example.crazyweather.services.interfaces.ISearchHistoryService
import com.example.crazyweather.services.interfaces.IWeatherApiService
import com.example.crazyweather.viewmodels.HistoryViewModel
import com.example.crazyweather.viewmodels.SearchResultViewModel
import com.example.crazyweather.viewmodels.SearchViewModel
import com.example.crazyweather.viewmodels.SharedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    val prodQualifier = named("prod")
    val mockQualifier = named("mock")

    single<IWeatherApiService> { FakeWeatherApiService() }
    single<ISearchHistoryService>(mockQualifier) { FakeSearchHistoryService() }
    single<ICitySearchService> (mockQualifier) { CitySearchService(get())}

    viewModel { HistoryViewModel(get(mockQualifier)) }
    viewModel { SearchViewModel(get(mockQualifier)) }
    viewModel { SearchResultViewModel(get(mockQualifier)) }
    viewModel { SharedViewModel() }
}