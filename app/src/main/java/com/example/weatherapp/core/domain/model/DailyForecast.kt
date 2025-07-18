package com.example.weatherapp.core.domain.model

data class DailyForecast(
    val dt: Long,
    val tempMin: Int,
    val tempMax: Int,
    val icon: String
)
