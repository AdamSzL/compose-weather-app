package com.example.weatherapp.weather.domain.use_cases

import com.example.weatherapp.weather.domain.WeatherTileData

class DeleteTileUseCase {
    operator fun invoke(tiles: List<WeatherTileData>, tileIdToDelete: String): List<WeatherTileData> {
        return tiles.filter { it.tileId != tileIdToDelete }
    }
}