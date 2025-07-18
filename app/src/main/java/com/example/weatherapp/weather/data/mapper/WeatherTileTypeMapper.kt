package com.example.weatherapp.weather.data.mapper

import com.example.weatherapp.weather.data.model.WeatherTileType
import com.example.weatherapp.weather.presentation.model.WeatherTileData

fun WeatherTileData.type(): WeatherTileType = when (this) {
    is WeatherTileData.Wind -> WeatherTileType.Wind
    is WeatherTileData.Rain -> WeatherTileType.Rain
    is WeatherTileData.Snow -> WeatherTileType.Snow
    is WeatherTileData.Pressure -> WeatherTileType.Pressure
    is WeatherTileData.Humidity -> WeatherTileType.Humidity
    is WeatherTileData.Cloudiness -> WeatherTileType.Cloudiness
    is WeatherTileData.Visibility -> WeatherTileType.Visibility
    is WeatherTileData.Uvi -> WeatherTileType.Uvi
    is WeatherTileData.Sunrise -> WeatherTileType.Sunrise
    is WeatherTileData.Sunset -> WeatherTileType.Sunset
}