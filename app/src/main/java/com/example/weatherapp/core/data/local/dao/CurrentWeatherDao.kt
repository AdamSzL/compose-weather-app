package com.example.weatherapp.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.core.data.local.entity.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {

    @Query("SELECT * FROM current_weather WHERE locationId = :locationId")
    suspend fun getWeatherByLocationId(locationId: Long): CurrentWeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentWeather(weather: CurrentWeatherEntity)

    @Query("DELETE FROM current_weather WHERE locationId = :locationId")
    suspend fun deleteCurrentWeather(locationId: Long)

}