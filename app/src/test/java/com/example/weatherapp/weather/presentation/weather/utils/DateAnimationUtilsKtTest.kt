package com.example.weatherapp.weather.presentation.weather.utils

import com.example.weatherapp.weather.presentation.utils.convertTimestampToHourMinute
import kotlinx.datetime.TimeZone
import org.junit.Assert.assertEquals
import org.junit.Test

class DateAnimationUtilsKtTest {

    @Test
    fun convertTimestampToHourMinute_timestamp_returnsHourMinuteString() {
        val timestamp = 1726636384L
        val expected = "05:13"
        val actual = convertTimestampToHourMinute(timestamp, TimeZone.UTC)
        assertEquals(expected, actual)
    }

    @Test
    fun convertTimestampToHourMinute_differentTimestamp_returnsHourMinuteString() {
        val timestamp = 1726680975L
        val expected = "17:36"
        val actual = convertTimestampToHourMinute(timestamp, TimeZone.UTC)
        assertEquals(expected, actual)
    }
}