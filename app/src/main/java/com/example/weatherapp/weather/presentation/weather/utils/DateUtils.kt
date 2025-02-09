package com.example.weatherapp.weather.presentation.weather.utils

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone

fun convertTimestampToHourMinute(timestamp: Long, timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
    val instant = Instant.fromEpochSeconds(timestamp)
    val localDateTime = instant.toLocalDateTime(timeZone)
    return "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
}