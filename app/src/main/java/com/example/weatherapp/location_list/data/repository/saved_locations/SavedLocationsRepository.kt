package com.example.weatherapp.location_list.data.repository.saved_locations

import com.example.weatherapp.core.domain.model.GeoLocation
import kotlinx.coroutines.flow.Flow

interface SavedLocationsRepository {

    suspend fun getAllLocations(): Flow<List<GeoLocation>>

    suspend fun getLocationById(locationId: Long): GeoLocation

    suspend fun insertLocation(location: GeoLocation)

    suspend fun deleteLocation(locationId: Long)

}