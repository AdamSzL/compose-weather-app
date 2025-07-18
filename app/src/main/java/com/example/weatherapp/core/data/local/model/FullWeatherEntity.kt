package com.example.weatherapp.core.data.local.model

import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity
import com.example.weatherapp.core.data.local.entity.DailyWeatherEntity
import com.example.weatherapp.core.data.local.entity.HourlyWeatherEntity

data class FullWeatherEntity(
    val locationId: Long,
    val current: CurrentWeatherEntity,
    val hourly: List<HourlyWeatherEntity>,
    val daily: List<DailyWeatherEntity>
)