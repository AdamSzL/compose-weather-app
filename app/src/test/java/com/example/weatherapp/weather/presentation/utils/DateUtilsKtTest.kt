package com.example.weatherapp.weather.presentation.utils

import kotlinx.datetime.Instant
import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilsKtTest {

    @Test
    fun `formatToHourMinute should return correct hour colon minutes time in UTC and Europe_Warsaw`() {
        val timestamp = Instant.parse("2025-07-06T12:35:00Z").epochSeconds

        assertEquals("12:35", formatToHourMinute(timestamp, "UTC"))
        assertEquals("14:35", formatToHourMinute(timestamp, "Europe/Warsaw"))
    }

    @Test
    fun `formatToMonthDay should return correct month slash day format in UTC and Europe_Warsaw`() {
        val timestamp = Instant.parse("2025-07-06T04:00:00Z").epochSeconds

        assertEquals("7/6", formatToMonthDay(timestamp, "UTC"))
        assertEquals("7/6", formatToMonthDay(timestamp, "Europe/Warsaw"))
    }

    @Test
    fun `formatToHourMinute should handle midnight correctly`() {
        val timestamp = Instant.parse("2025-01-01T00:00:00Z").epochSeconds

        assertEquals("00:00", formatToHourMinute(timestamp, "UTC"))
    }

    @Test
    fun `formatToMonthDay should correctly handle single-digit day and month`() {
        val timestamp = Instant.parse("2025-01-05T15:00:00Z").epochSeconds

        assertEquals("1/5", formatToMonthDay(timestamp, "UTC"))
    }

}