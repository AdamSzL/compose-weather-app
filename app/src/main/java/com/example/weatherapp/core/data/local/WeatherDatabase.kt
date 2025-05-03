package com.example.weatherapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.core.data.local.dao.SavedWeatherDao
import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity
import com.example.weatherapp.location_list.data.local.dao.SavedLocationsDao
import com.example.weatherapp.location_list.data.local.entity.SavedLocationEntity

@Database(
    entities = [SavedLocationEntity::class, CurrentWeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun savedLocationsDao(): SavedLocationsDao
    abstract fun savedWeatherDao(): SavedWeatherDao
}