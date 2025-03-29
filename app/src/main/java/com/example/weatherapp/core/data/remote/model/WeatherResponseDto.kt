package com.example.weatherapp.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerialName("timezone_offset") val timezoneOffset: Int,
    val current: CurrentWeatherDto,
    val hourly: List<HourlyWeatherDto>? = null,
    val daily: List<DailyWeatherDto>? = null
)