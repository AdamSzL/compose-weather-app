package com.example.weatherapp.core.data.local

import com.example.weatherapp.core.data.local.model.FullWeatherEntity

interface WeatherLocalDataSource {

    suspend fun getFullWeather(locationId: Long): FullWeatherEntity?

    suspend fun saveFullWeather(fullWeatherEntity: FullWeatherEntity)
}