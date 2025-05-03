package com.example.weatherapp.core.data.remote.mapper

import com.example.weatherapp.core.data.remote.model.CurrentWeatherDto
import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DetailedWeather

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
