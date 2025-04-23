package com.example.crazyweather.services.implementations

import com.example.crazyweather.models.entities.CityWeather
import com.example.crazyweather.models.entities.WeatherMetrics
import com.example.crazyweather.services.interfaces.ICitySearchService
import com.example.crazyweather.services.interfaces.IWeatherService
import kotlin.math.sqrt

class CitySearchService(
    private val weatherService: IWeatherService
) : ICitySearchService {

    override suspend fun findBestMatchingCities(userMetrics: WeatherMetrics): List<CityWeather> {
        val allCities = weatherService.getCitiesAverageWeather(7)
        return calculateMatches(allCities, userMetrics)
    }

    override suspend fun getCityForecast(cityName: String, duringDays: Int): List<WeatherMetrics> {
        return weatherService.getCityForecast(cityName, duringDays)
    }

    private fun calculateMatches(
        cities: List<CityWeather>,
        userMetrics: WeatherMetrics
    ): List<CityWeather> {
        cities.map { it.matchPercentage = calculateMatchScore(it.metrics, userMetrics) }
        return cities.sortedByDescending { it.matchPercentage }
    }

    private fun calculateMatchScore(cityMetrics: WeatherMetrics, userMetrics: WeatherMetrics): Int {
        val maxValues = mapOf(
            "temperature" to 50.0,
            "windSpeed" to 30.0,
            "humidity" to 100.0,
            "cloudiness" to 100.0
        )

        val metricsToCompare = mutableListOf<Pair<Double, Double>>()

        var normCity = cityMetrics.temperature / maxValues["temperature"]!!
        var normUser = userMetrics.temperature / maxValues["temperature"]!!
        metricsToCompare.add(normCity to normUser)

        normCity = cityMetrics.windSpeed / maxValues["windSpeed"]!!
        normUser = userMetrics.windSpeed / maxValues["windSpeed"]!!
        metricsToCompare.add(normCity to normUser)

        normCity = cityMetrics.humidity / maxValues["humidity"]!!
        normUser = userMetrics.humidity / maxValues["humidity"]!!
        metricsToCompare.add(normCity to normUser)

        normCity = cityMetrics.cloudiness / maxValues["cloudiness"]!!
        normUser = userMetrics.cloudiness / maxValues["cloudiness"]!!
        metricsToCompare.add(normCity to normUser)

        if (metricsToCompare.isEmpty()) return 0

        var sumOfSquares = 0.0
        for ((cityNorm, userNorm) in metricsToCompare) {
            val diff = cityNorm - userNorm
            sumOfSquares += diff * diff
        }

        val euclideanDistance = sqrt(sumOfSquares)
        val maxPossibleDistance = sqrt(metricsToCompare.size.toDouble())
        val matchPercentage = 100 - (euclideanDistance / maxPossibleDistance) * 100
        return matchPercentage.coerceIn(0.0, 100.0).toInt()
    }
}