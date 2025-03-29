package com.example.weatherapp.weather.domain.use_cases

import com.example.weatherapp.weather.presentation.model.WeatherTileData

class MoveTileUseCase {
    operator fun invoke(tiles: List<WeatherTileData>, from: Int, to: Int): List<WeatherTileData> {
        return tiles.toMutableList().apply {
            add(to, removeAt(from))
        }
    }
}