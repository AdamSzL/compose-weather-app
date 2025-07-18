package com.example.weatherapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.core.data.local.dao.CurrentWeatherDao
import com.example.weatherapp.core.data.local.dao.DailyWeatherDao
import com.example.weatherapp.core.data.local.dao.HourlyWeatherDao
import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity
import com.example.weatherapp.core.data.local.entity.DailyWeatherEntity
import com.example.weatherapp.core.data.local.entity.HourlyWeatherEntity
import com.example.weatherapp.location_list.data.local.dao.SavedLocationsDao
import com.example.weatherapp.location_list.data.local.entity.SavedLocationEntity

@Database(
    entities = [SavedLocationEntity::class, CurrentWeatherEntity::class, HourlyWeatherEntity::class, DailyWeatherEntity::class],
    version = 3,
    exportSchema = false
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun savedLocationsDao(): SavedLocationsDao
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun hourlyWeatherDao(): HourlyWeatherDao
    abstract fun dailyWeatherDao(): DailyWeatherDao
}