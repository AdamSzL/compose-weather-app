package com.example.weatherapp.core.data.mapper

import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity
import com.example.weatherapp.core.data.remote.model.CurrentWeatherDto

fun CurrentWeatherDto.toEntity(locationId: Long): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        locationId = locationId,
        dt = dt,
        sunrise = sunrise,
        sunset = sunset,
        temp = temp,
        feelsLike = feelsLike,
        pressure = pressure,
        humidity = humidity,
        dewPoint = dewPoint,
        uvi = uvi,
        clouds = clouds,
        visibility = visibility,
        windSpeed = windSpeed,
        windDeg = windDeg,
        windGust = windGust,
        weatherIcon = weather.firstOrNull()?.icon ?: "01d",
        weatherDescription = weather.firstOrNull()?.description ?: "Unknown",
        rain = rain?.oneHour,
        snow = snow?.oneHour
    )
}