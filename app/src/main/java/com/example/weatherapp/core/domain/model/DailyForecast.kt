package com.example.weatherapp.core.domain.model

data class DailyForecast(
    val dt: Long,
    val minTemp: Double,
    val maxTemp: Double,
    val weatherDescription: String,
    val weatherIcon: String,
)
