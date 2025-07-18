package com.example.weatherapp.core.data.mapper

import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity
import com.example.weatherapp.core.data.local.entity.DailyWeatherEntity
import com.example.weatherapp.core.data.local.entity.HourlyWeatherEntity
import com.example.weatherapp.core.data.local.model.FullWeatherEntity
import com.example.weatherapp.core.data.remote.model.CurrentWeatherDto
import com.example.weatherapp.core.data.remote.model.DailyWeatherDto
import com.example.weatherapp.core.data.remote.model.HourlyWeatherDto
import com.example.weatherapp.core.data.remote.model.WeatherResponseDto

fun CurrentWeatherDto.toEntity(locationId: Long, timezone: String): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        locationId = locationId,
        timezone = timezone,
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

fun HourlyWeatherDto.toEntity(locationId: Long): HourlyWeatherEntity {
    return HourlyWeatherEntity(
        locationId = locationId,
        dt = dt,
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
        pop = pop
    )
}

fun DailyWeatherDto.toEntity(locationId: Long): DailyWeatherEntity {
    return DailyWeatherEntity(
        locationId = locationId,
        dt = dt,
        sunrise = sunrise,
        sunset = sunset,
        moonrise = moonrise,
        moonset = moonset,
        moonPhase = moonPhase,
        tempMin = temp.min,
        tempMax = temp.max,
        feelsLikeDay = feelsLike.day,
        feelsLikeNight = feelsLike.night,
        pressure = pressure,
        humidity = humidity,
        clouds = clouds,
        pop = pop,
        uvi = uvi,
        weatherIcon = weather.firstOrNull()?.icon ?: "01d",
        weatherDescription = weather.firstOrNull()?.description ?: "Unknown"
    )
}

fun WeatherResponseDto.toFullWeatherEntity(locationId: Long): FullWeatherEntity {
    val currentEntity = current.toEntity(locationId, timezone)
    val hourlyEntities = hourly.map { it.toEntity(locationId) }
    val dailyEntities = daily.map { it.toEntity(locationId) }

    return FullWeatherEntity(
        locationId = locationId,
        current = currentEntity,
        hourly = hourlyEntities.take(24),
        daily = dailyEntities.take(24)
    )
}