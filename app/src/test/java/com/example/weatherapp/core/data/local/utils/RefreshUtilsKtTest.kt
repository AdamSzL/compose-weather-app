package com.example.weatherapp.core.data.local.utils

import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class RefreshUtilsKtTest {

    private fun fakeCurrentWeatherEntity(
        locationId: Long = 1L,
        timezone: String = "Europe/Warsaw",
        dt: Long = System.currentTimeMillis(),
        temp: Double = 20.0,
        feelsLike: Double = 20.0,
        pressure: Int = 1000,
        humidity: Int = 50,
        dewPoint: Double = 10.0,
        uvi: Double = 5.0,
        clouds: Int = 0,
        visibility: Int = 10000,
        windSpeed: Double = 5.0,
        windDeg: Int = 90,
        windGust: Double? = null,
        weatherIcon: String = "01d",
        weatherDescription: String = "Clear sky",
        rain: Double? = null,
        snow: Double? = null,
        sunrise: Long = 0L,
        sunset: Long = 0L,
    ): CurrentWeatherEntity {
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
            weatherIcon = weatherIcon,
            weatherDescription = weatherDescription,
            rain = rain,
            snow = snow
        )
    }

    @Test
    fun `isExpired returns false when data is fresh`() {
        val currentTime = System.currentTimeMillis()
        val dtInSeconds = currentTime / 1000L
        val entity = fakeCurrentWeatherEntity(dt = dtInSeconds)

        val result = entity.isExpired(currentTimeMillis = currentTime)

        assertFalse(result)
    }

    @Test
    fun `isExpired returns true when data is older than 30 minutes`() {
        val currentTime = System.currentTimeMillis()
        val thirtyOneMinutesAgoInMillis = currentTime - (31 * 60 * 1000L)
        val dtInSeconds = thirtyOneMinutesAgoInMillis / 1000L
        val entity = fakeCurrentWeatherEntity(dt = dtInSeconds)

        val result = entity.isExpired(currentTimeMillis = currentTime)

        assertTrue(result)
    }

    @Test
    fun `isExpired returns false when data is slightly younger than 30 minutes`() {
        val currentTime = System.currentTimeMillis()
        val twentyNineMinutesAgoInMillis = currentTime - (29 * 60 * 1000L)
        val dtInSeconds = twentyNineMinutesAgoInMillis / 1000L
        val entity = fakeCurrentWeatherEntity(dt = dtInSeconds)

        val result = entity.isExpired(currentTimeMillis = currentTime)

        assertFalse(result)
    }

    @Test
    fun `isRefreshable returns false when data is very fresh`() {
        val currentTime = System.currentTimeMillis()
        val dtInSeconds = currentTime / 1000L
        val entity = fakeCurrentWeatherEntity(dt = dtInSeconds)

        val result = entity.isRefreshable(currentTimeMillis = currentTime)

        assertFalse(result)
    }

    @Test
    fun `isRefreshable returns true when data is older than 10 minutes`() {
        val currentTime = System.currentTimeMillis()
        val elevenMinutesAgoInMillis = currentTime - (11 * 60 * 1000L)
        val dtInSeconds = elevenMinutesAgoInMillis / 1000L
        val entity = fakeCurrentWeatherEntity(dt = dtInSeconds)

        val result = entity.isRefreshable(currentTimeMillis = currentTime)

        assertTrue(result)
    }

    @Test
    fun `isRefreshable returns false when data is slightly younger than 10 minutes`() {
        val currentTime = System.currentTimeMillis()
        val nineMinutesAgoInMillis = currentTime - (9 * 60 * 1000L)
        val dtInSeconds = nineMinutesAgoInMillis / 1000L
        val entity = fakeCurrentWeatherEntity(dt = dtInSeconds)

        val result = entity.isRefreshable(currentTimeMillis = currentTime)

        assertFalse(result)
    }
}