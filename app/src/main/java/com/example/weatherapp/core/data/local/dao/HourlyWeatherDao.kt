package com.example.weatherapp.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.core.data.local.entity.HourlyWeatherEntity

@Dao
interface HourlyWeatherDao {

    @Query("SELECT * FROM hourly_weather WHERE locationId = :locationId ORDER BY dt ASC")
    suspend fun getHourlyWeatherByLocationId(locationId: Long): List<HourlyWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hourlyList: List<HourlyWeatherEntity>)

    @Query("DELETE FROM hourly_weather WHERE locationId = :locationId")
    suspend fun deleteHourlyWeatherByLocationId(locationId: Long)

}