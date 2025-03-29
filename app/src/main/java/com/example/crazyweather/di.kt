package com.example.crazyweather

import com.example.crazyweather.services.fakes.FakeWeatherApiService
import com.example.crazyweather.services.implementations.CitySearchService
import com.example.crazyweather.services.implementations.SearchHistoryService
import com.example.crazyweather.services.interfaces.ICitySearchService
import com.example.crazyweather.services.interfaces.ISearchHistoryService
import com.example.crazyweather.services.interfaces.IWeatherApiService
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    val prodQualifier = named("prod")
    val mockQualifier = named("mock")

    single<ICitySearchService> (prodQualifier) { CitySearchService(
        weatherApi = get()
    ) }
    single<ISearchHistoryService>(prodQualifier) { SearchHistoryService() }
    single<IWeatherApiService>(mockQualifier) { FakeWeatherApiService() }
}