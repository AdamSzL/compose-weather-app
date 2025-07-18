package com.example.weatherapp.core.domain.model

data class DetailedWeather(
    val timezone: String,
    val temperature: Int,
    val feelsLike: Int,
    val description: String,
    val icon: String,
    val pressure: Int,
    val humidity: Int,
    val uvi: Double,
    val clouds: Int,
    val visibility: Double,
    val windSpeed: Double,
    val windDeg: Int,
    val rain: Double?,
    val snow: Double?,
    val sunrise: Long,
    val sunset: Long,
    val hourlyForecast: List<HourlyForecast>,
    val dailyForecast: List<DailyForecast>
)