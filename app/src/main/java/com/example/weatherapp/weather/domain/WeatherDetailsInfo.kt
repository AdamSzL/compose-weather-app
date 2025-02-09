package com.example.weatherapp.weather.domain

data class WeatherDetailsInfo(
    val windSpeed: Double,
    val windDirection: Int,
    val humidity: Int,
    val pressure: Int,
    val precipitation: Double,
    val visibility: Int,
    val cloudiness: Int,
)
