package com.example.weatherapp.weather.presentation.weather.fake

import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.weather.presentation.mapper.toWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.mapper.toWeatherTiles

val fakeBriefWeather = BriefWeather(
    temperature = 10.5,
    icon = "10d",
    description = "moderate rain"
)

val fakeDetailedWeather = DetailedWeather(
    temperature = 11.05,
    feelsLike = 9.78,
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
    sunset = 1684977332
)

val fakeWeatherHeaderInfo = fakeDetailedWeather.toWeatherHeaderInfo()

val fakeWeatherTileData = fakeDetailedWeather.toWeatherTiles()