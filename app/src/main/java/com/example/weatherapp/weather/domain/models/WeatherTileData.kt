package com.example.weatherapp.weather.domain.models

import java.util.UUID

sealed class WeatherTileData(val tileId: String = UUID.randomUUID().toString()) {

    data class WindSpeed(
        val windSpeed: Double,
    ) : WeatherTileData()

    data class WindDirection(
        val windDirection: Int
    ): WeatherTileData()

    data class Rain(
        val rain: Double
    ) : WeatherTileData()

    data class Snow(
        val snow: Double
    ) : WeatherTileData()

    data class Pressure(
        val pressure: Int
    ) : WeatherTileData()

    data class Humidity(
        val humidity: Int
    ) : WeatherTileData()

    data class Cloudiness(
        val cloudiness: Int
    ) : WeatherTileData()

    data class Visibility(
        val visibility: Int
    ) : WeatherTileData()

    data class Sunrise(
        val sunrise: Long,
    ) : WeatherTileData()

    data class Sunset(
        val sunset: Long
    ) : WeatherTileData()

}
