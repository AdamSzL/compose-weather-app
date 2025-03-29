package com.example.weatherapp.core.domain.model

data class HourlyForecast(
    val dt: Long,
    val temperature: Double,
    val weatherIcon: String,
    val precipitationProbability: Double,
)
