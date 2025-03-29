package com.example.weatherapp.core.data.remote.model

import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DetailedWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDto(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName("dew_point") val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_deg") val windDeg: Int,
    @SerialName("wind_gust") val windGust: Double? = null,
    val weather: List<WeatherDescriptionDto>,
    val rain: RainSnowDto? = null,
    val snow: RainSnowDto? = null,
)

fun CurrentWeatherDto.toBriefWeather(): BriefWeather {
    return BriefWeather(
        temperature = temp,
        icon = weather.firstOrNull()?.icon ?: "01d",
        description = weather.firstOrNull()?.description ?: "Unknown",
    )
}

fun CurrentWeatherDto.toDetailedWeather(): DetailedWeather {
    return DetailedWeather(
        temperature = temp,
        feelsLike = feelsLike,
        description = weather.firstOrNull()?.description ?: "Unknown",
        icon = weather.firstOrNull()?.icon ?: "01d",
        pressure = pressure,
        humidity = humidity,
        uvi = uvi,
        clouds = clouds,
        visibility = visibility / 1000.0,
        windSpeed = windSpeed,
        windDeg = windDeg,
        rain = rain?.oneHour,
        snow = snow?.oneHour,
        sunrise = sunrise,
        sunset = sunset,
    )
}
