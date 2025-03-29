package com.example.weatherapp.weather.presentation.mapper

import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.weather.presentation.model.WeatherHeaderInfo

fun DetailedWeather.toWeatherHeaderInfo(): WeatherHeaderInfo {
    return WeatherHeaderInfo(
        temperature = temperature,
        feelsLike = feelsLike,
        description = description,
        icon = icon
    )
}