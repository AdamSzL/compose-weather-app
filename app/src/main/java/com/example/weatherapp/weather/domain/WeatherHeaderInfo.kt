package com.example.weatherapp.weather.domain

data class WeatherHeaderInfo(
    val temperature: Double,
    val feelsLike: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val weatherCondition: String,
    val weatherDescription: String,
    val weatherIcon: String,
)
