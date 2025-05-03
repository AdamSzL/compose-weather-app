package com.example.weatherapp.location_list.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.location_list.data.local.entity.SavedLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedLocationsDao {

    @Query("SELECT * FROM saved_locations")
    fun getAllLocations(): Flow<List<SavedLocationEntity>>

    @Query("SELECT * FROM saved_locations WHERE id = :locationId")
    suspend fun getLocationById(locationId: Long): SavedLocationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: SavedLocationEntity): Long

    @Query("DELETE FROM saved_locations WHERE id = :locationId")
    suspend fun deleteLocation(locationId: Long)

}