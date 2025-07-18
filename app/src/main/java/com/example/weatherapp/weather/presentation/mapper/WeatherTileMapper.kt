package com.example.weatherapp.weather.presentation.mapper

import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.weather.data.mapper.type
import com.example.weatherapp.weather.data.model.WeatherTileType
import com.example.weatherapp.weather.presentation.model.WeatherTileData

fun DetailedWeather.toWeatherTiles(order: List<WeatherTileType> = emptyList()): List<WeatherTileData> {
    val allTiles = listOf(
        WeatherTileData.Wind(windSpeed, windDeg),
        WeatherTileData.Rain(rain ?: 0.0),
        WeatherTileData.Snow(snow ?: 0.0),
        WeatherTileData.Pressure(pressure),
        WeatherTileData.Humidity(humidity),
        WeatherTileData.Cloudiness(clouds),
        WeatherTileData.Visibility(visibility),
        WeatherTileData.Uvi(uvi),
        WeatherTileData.Sunrise(sunrise),
        WeatherTileData.Sunset(sunset)
    )
    return if (order.isEmpty()) allTiles else order.mapNotNull { type ->
        allTiles.find { it.type() == type }
    }
}