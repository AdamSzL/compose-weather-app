package com.example.weatherapp.weather.presentation.weather.fake

import com.example.weatherapp.weather.domain.models.WeatherInfo
import com.example.weatherapp.weather.domain.models.toWeatherHeaderInfo
import com.example.weatherapp.weather.domain.models.toWeatherTileDataList

val fakeWeatherInfo = WeatherInfo(
    cityName = "Province of Turin",
    country = "IT",
    temperature = 11.05,
    feelsLike = 9.78,
    minTemperature = 9.91,
    maxTemperature = 13.67,
    humidity = 60,
    pressure = 1021,
    windSpeed = 4.09,
    windDirection = 121,
    visibility = 10,
    rain = 2.23,
    snow = 1.57,
    cloudiness = 86,
    weatherCondition = "Rain",
    weatherDescription = "moderate rain",
    weatherIcon = "10d",
    sunrise = 1726636384,
    sunset = 1726680975
)

val fakeWeatherHeaderInfo = fakeWeatherInfo.toWeatherHeaderInfo()

val fakeWeatherTileData = fakeWeatherInfo.toWeatherTileDataList()