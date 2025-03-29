package com.example.weatherapp.core.data.remote.model

import com.example.weatherapp.core.domain.model.HourlyForecast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeatherDto(
    val dt: Long,
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
    val pop: Double
)

fun List<HourlyWeatherDto>.toHourlyForecastList(): List<HourlyForecast> {
    return map { dto ->
        HourlyForecast(
            dt = dto.dt,
            temperature = dto.temp,
            weatherIcon = dto.weather.firstOrNull()?.icon ?: "01d",
            precipitationProbability = dto.pop,
        )
    }
}