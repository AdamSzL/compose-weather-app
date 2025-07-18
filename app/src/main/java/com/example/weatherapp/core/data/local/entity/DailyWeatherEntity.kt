package com.example.weatherapp.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_weather")
data class DailyWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val locationId: Long,
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val moonPhase: Double,
    val tempMin: Double,
    val tempMax: Double,
    val feelsLikeDay: Double,
    val feelsLikeNight: Double,
    val pressure: Int,
    val humidity: Int,
    val clouds: Int,
    val pop: Double,
    val uvi: Double,
    val weatherIcon: String,
    val weatherDescription: String
)