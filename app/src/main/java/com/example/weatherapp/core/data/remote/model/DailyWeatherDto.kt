package com.example.weatherapp.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherDto(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    @SerialName("moon_phase") val moonPhase: Double,
    val summary: String? = null,
    val temp: DailyTemperatureDto,
    @SerialName("feels_like") val feelsLike: DailyFeelsLikeDto,
    val pressure: Int,
    val humidity: Int,
    val weather: List<WeatherDescriptionDto>,
    val clouds: Int,
    val pop: Double,
    val uvi: Double
)

@Serializable
data class DailyTemperatureDto(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

@Serializable
data class DailyFeelsLikeDto(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)