package com.example.weatherapp.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.core.data.local.entity.DailyWeatherEntity

@Dao
interface DailyWeatherDao {

    @Query("SELECT * FROM daily_weather WHERE locationId = :locationId ORDER BY dt ASC")
    suspend fun getDailyWeatherByLocationId(locationId: Long): List<DailyWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dailyList: List<DailyWeatherEntity>)

    @Query("DELETE FROM daily_weather WHERE locationId = :locationId")
    suspend fun deleteDailyWeatherByLocationId(locationId: Long)

}