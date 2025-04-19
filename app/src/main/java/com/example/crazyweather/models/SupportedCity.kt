package com.example.crazyweather.models

data class SupportedCity(
    val name: String,
    val localizedName: String
)

object SupportedCities {
    val cities = listOf(
        SupportedCity("Saratov", "Саратов"),
        SupportedCity("Yakutsk", "Якутск"),
        SupportedCity("Surgut", "Сургут"),
        SupportedCity("Moscow", "Москва")
    )

    fun getByName(name: String): SupportedCity? {
        return cities.firstOrNull { it.name.equals(name, ignoreCase = true) }
    }
}