package com.example.weatherapp.weather.data.repository

import com.example.weatherapp.weather.data.model.WeatherTileType
import com.example.weatherapp.weather.presentation.model.WeatherTileData

interface TileLayoutRepository {

    suspend fun loadTileOrder(): List<WeatherTileType>

    suspend fun saveTileOrder(tiles: List<WeatherTileData>)

    suspend fun resetTileOrder()

}