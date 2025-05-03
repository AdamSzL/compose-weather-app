package com.example.weatherapp.core.data.local.utils

import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity

fun CurrentWeatherEntity.isExpired(currentTimeMillis: Long = System.currentTimeMillis()): Boolean {
    val thirtyMinutesMillis = 30 * 60 * 1000L
    val dtMillis = dt * 1000L
    return (currentTimeMillis - dtMillis) > thirtyMinutesMillis
}

fun CurrentWeatherEntity.isRefreshable(currentTimeMillis: Long = System.currentTimeMillis()): Boolean {
    val fiveMinutesMillis = 5 * 60 * 1000L
    val dtMillis = dt * 1000L
    return (currentTimeMillis - dtMillis) > fiveMinutesMillis
}