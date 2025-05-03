package com.example.weatherapp.core.data.local

import com.example.weatherapp.core.data.local.dao.SavedWeatherDao
import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity

class WeatherLocalDataSourceImpl(
    private val savedWeatherDao: SavedWeatherDao,
): WeatherLocalDataSource {

    override suspend fun getSavedWeather(locationId: Long): CurrentWeatherEntity? {
        return savedWeatherDao.getWeatherByLocationId(locationId)
    }

    override suspend fun saveCurrentWeather(weather: CurrentWeatherEntity) {
        return savedWeatherDao.saveCurrentWeather(weather)
    }
}