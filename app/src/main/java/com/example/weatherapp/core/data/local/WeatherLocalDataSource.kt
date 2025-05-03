package com.example.weatherapp.core.data.local

import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity

interface WeatherLocalDataSource {

    suspend fun getSavedWeather(locationId: Long): CurrentWeatherEntity?

    suspend fun saveCurrentWeather(weather: CurrentWeatherEntity)
}