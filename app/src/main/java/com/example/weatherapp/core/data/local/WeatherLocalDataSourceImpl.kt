package com.example.weatherapp.core.data.local

import com.example.weatherapp.core.data.local.dao.CurrentWeatherDao
import com.example.weatherapp.core.data.local.dao.DailyWeatherDao
import com.example.weatherapp.core.data.local.dao.HourlyWeatherDao
import com.example.weatherapp.core.data.local.model.FullWeatherEntity

class WeatherLocalDataSourceImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val dailyWeatherDao: DailyWeatherDao,
    private val hourlyWeatherDao: HourlyWeatherDao
): WeatherLocalDataSource {

    override suspend fun getFullWeather(locationId: Long): FullWeatherEntity? {
        val current = currentWeatherDao.getWeatherByLocationId(locationId) ?: return null
        val hourly = hourlyWeatherDao.getHourlyWeatherByLocationId(locationId)
        val daily = dailyWeatherDao.getDailyWeatherByLocationId(locationId)
        return FullWeatherEntity(locationId, current, hourly, daily)
    }

    override suspend fun saveFullWeather(fullWeatherEntity: FullWeatherEntity) {
        hourlyWeatherDao.deleteHourlyWeatherByLocationId(fullWeatherEntity.locationId)
        dailyWeatherDao.deleteDailyWeatherByLocationId(fullWeatherEntity.locationId)

        currentWeatherDao.saveCurrentWeather(fullWeatherEntity.current)
        hourlyWeatherDao.insertAll(fullWeatherEntity.hourly)
        dailyWeatherDao.insertAll(fullWeatherEntity.daily)
    }

}