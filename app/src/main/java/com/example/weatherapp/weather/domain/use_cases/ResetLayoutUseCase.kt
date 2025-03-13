package com.example.weatherapp.weather.domain.use_cases

import com.example.weatherapp.weather.domain.models.WeatherTileData

class ResetLayoutUseCase {
    operator fun invoke(weatherTileDataHistory: List<List<WeatherTileData>>, currentWeatherTileDataIndex: Int): Triple<List<List<WeatherTileData>>, List<WeatherTileData>, Int> {
        val historyUntilCurrent = weatherTileDataHistory.subList(0, currentWeatherTileDataIndex + 1)
        val newHistory = historyUntilCurrent.toMutableList().apply {
            add(weatherTileDataHistory.first())
        }
        return Triple(newHistory, weatherTileDataHistory.first(), currentWeatherTileDataIndex + 1)
    }
}