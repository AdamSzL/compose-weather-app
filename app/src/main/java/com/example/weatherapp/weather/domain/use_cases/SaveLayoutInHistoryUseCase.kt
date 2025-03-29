package com.example.weatherapp.weather.domain.use_cases

import com.example.weatherapp.weather.presentation.model.WeatherTileData

class SaveLayoutInHistoryUseCase {
    operator fun invoke(weatherTileDataHistory: List<List<WeatherTileData>>, weatherTileData: List<WeatherTileData>, currentWeatherTileDataIndex: Int): Pair<List<List<WeatherTileData>>, Int> {
        val historyUntilCurrent = weatherTileDataHistory.subList(0, currentWeatherTileDataIndex + 1)
        val newHistory = historyUntilCurrent.toMutableList().apply {
            add(weatherTileData)
        }
        return Pair(newHistory, currentWeatherTileDataIndex + 1)
    }
}