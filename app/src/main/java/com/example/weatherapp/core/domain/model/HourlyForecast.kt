package com.example.weatherapp.core.domain.model

data class HourlyForecast(
    val dt: Long,
    val temperature: Int,
    val icon: String
)