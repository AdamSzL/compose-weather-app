package com.example.weatherapp.core.data.local.mapper

import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity
import com.example.weatherapp.core.data.local.entity.DailyWeatherEntity
import com.example.weatherapp.core.data.local.entity.HourlyWeatherEntity
import com.example.weatherapp.core.data.local.model.FullWeatherEntity
import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DailyForecast
import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.core.domain.model.HourlyForecast

fun CurrentWeatherEntity.toBriefWeather(): BriefWeather {
    return BriefWeather(
        temperature = temp.toInt(),
        icon = weatherIcon,
        description = weatherDescription
    )
}

fun FullWeatherEntity.toDetailedWeather(): DetailedWeather {
    return DetailedWeather(
        timezone = current.timezone,
        temperature = current.temp.toInt(),
        feelsLike = current.feelsLike.toInt(),
        description = current.weatherDescription,
        icon = current.weatherIcon,
        pressure = current.pressure,
        humidity = current.humidity,
        uvi = current.uvi,
        clouds = current.clouds,
        visibility = current.visibility / 1000.0,
        windSpeed = current.windSpeed,
        windDeg = current.windDeg,
        rain = current.rain,
        snow = current.snow,
        sunrise = current.sunrise,
        sunset = current.sunset,
        hourlyForecast = hourly.map { it.toHourlyForecast() },
        dailyForecast = daily.map { it.toDailyForecast() }
    )
}

fun HourlyWeatherEntity.toHourlyForecast(): HourlyForecast {
    return HourlyForecast(
        dt = dt,
        temperature = temp.toInt(),
        icon = weatherIcon
    )
}

fun DailyWeatherEntity.toDailyForecast(): DailyForecast {
    return DailyForecast(
        dt = dt,
        tempMin = tempMin.toInt(),
        tempMax = tempMax.toInt(),
        icon = weatherIcon
    )
}