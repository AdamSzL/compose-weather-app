package com.example.weatherapp.weather.presentation.mapper

import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.weather.presentation.model.WeatherTileData

fun DetailedWeather.toWeatherTiles(): List<WeatherTileData> {
    return listOf(
        WeatherTileData.WindSpeed(windSpeed),
        WeatherTileData.WindDirection(windDeg),
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
}