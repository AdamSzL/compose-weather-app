package com.example.weatherapp.weather.presentation.fake

import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DailyForecast
import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.core.domain.model.HourlyForecast
import com.example.weatherapp.weather.presentation.mapper.toWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.mapper.toWeatherTiles

val fakeBriefWeather = BriefWeather(
    temperature = 10,
    icon = "10d",
    description = "moderate rain"
)

val fakeDetailedWeather = DetailedWeather(
    timezone = "Europe/Warsaw",
    temperature = 11,
    feelsLike = 9,
    description = "moderate rain",
    icon = "10d",
    pressure = 1021,
    humidity = 60,
    uvi = 3.16,
    clouds = 86,
    visibility = 8.55,
    windSpeed = 4.09,
    windDeg = 121,
    rain = 2.23,
    snow = 1.57,
    sunrise = 1684926645,
    sunset = 1684977332,
    hourlyForecast = listOf(
        HourlyForecast(dt = 1684980000L, temperature = 10, icon = "10d"),
        HourlyForecast(dt = 1684983600L, temperature = 10, icon = "04d"),
        HourlyForecast(dt = 1684987200L, temperature = 11, icon = "01d"),
    ),
    dailyForecast = listOf(
        DailyForecast(dt = 1684965600L, tempMin = 8, tempMax = 12, icon = "10d"),
        DailyForecast(dt = 1685052000L, tempMin = 9, tempMax = 13, icon = "04d"),
        DailyForecast(dt = 1685138400L, tempMin = 7, tempMax = 11, icon = "03d"),
    )
)

val fakeWeatherHeaderInfo = fakeDetailedWeather.toWeatherHeaderInfo()

val fakeWeatherTileData = fakeDetailedWeather.toWeatherTiles()