package com.example.weatherapp.core.data.local.mapper

import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity
import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DetailedWeather

fun CurrentWeatherEntity.toBriefWeather(): BriefWeather {
    return BriefWeather(
        temperature = temp,
        icon = weatherIcon,
        description = weatherDescription
    )
}

fun CurrentWeatherEntity.toDetailedWeather(): DetailedWeather {
    return DetailedWeather(
        temperature = temp,
        feelsLike = feelsLike,
        description = weatherDescription,
        icon = weatherIcon,
        pressure = pressure,
        humidity = humidity,
        uvi = uvi,
        clouds = clouds,
        visibility = visibility / 1000.0,
        windSpeed = windSpeed,
        windDeg = windDeg,
        rain = rain,
        snow = snow,
        sunrise = sunrise,
        sunset = sunset
    )
}
