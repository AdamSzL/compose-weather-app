package com.example.weatherapp.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
    @PrimaryKey val locationId: Long,

    val dt: Long,
    val sunrise: Long,
    val sunset: Long,

    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,

    val windSpeed: Double,
    val windDeg: Int,
    val windGust: Double? = null,

    val weatherIcon: String,
    val weatherDescription: String,

    val rain: Double?,
    val snow: Double?,
)
