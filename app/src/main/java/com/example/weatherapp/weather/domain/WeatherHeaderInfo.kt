package com.example.weatherapp.weather.domain

data class WeatherHeaderInfo(
    val cityName: String,
    val country: String,
    val temperature: Double,
    val feelsLike: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val weatherCondition: String,
    val weatherDescription: String,
    val weatherIcon: String,
)
