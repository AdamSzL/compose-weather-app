package com.example.weatherapp.weather.presentation.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

fun convertTimestampToHourMinute(timestamp: Long, timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
    val instant = Instant.fromEpochSeconds(timestamp)
    val localDateTime = instant.toLocalDateTime(timeZone)
    return "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun formatToHourMinute(timestamp: Long, timezone: String): String {
    val instant = Instant.fromEpochSeconds(timestamp)
    val tz = TimeZone.of(timezone)
    val localTime = instant.toLocalDateTime(tz).time
    return localTime.format(LocalTime.Format { byUnicodePattern("HH:mm") })
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun formatToMonthDay(timestamp: Long, timezone: String): String {
    val instant = Instant.fromEpochSeconds(timestamp)
    val tz = TimeZone.of(timezone)
    val localDate = instant.toLocalDateTime(tz).date
    return localDate.format(LocalDate.Format { byUnicodePattern("M/d") })
}