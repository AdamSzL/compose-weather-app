package com.example.weatherapp.location_list.data.repository.saved_locations

import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.location_list.data.local.dao.SavedLocationsDao
import com.example.weatherapp.location_list.data.mapper.toGeoLocation
import com.example.weatherapp.location_list.data.mapper.toSavedLocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SavedLocationsRepositoryImpl(
    private val savedLocationsDao: SavedLocationsDao,
): SavedLocationsRepository {

    override suspend fun getAllLocations(): Flow<List<GeoLocation>> {
        return savedLocationsDao
            .getAllLocations()
            .map { savedLocationsEntities ->
                savedLocationsEntities.map {
                    it.toGeoLocation()
                }
            }
    }

    override suspend fun getLocationById(locationId: Long): GeoLocation {
        return savedLocationsDao.getLocationById(locationId).toGeoLocation()
    }

    override suspend fun insertLocation(location: GeoLocation) {
        savedLocationsDao.insertLocation(location.toSavedLocationEntity())
    }

    override suspend fun deleteLocation(locationId: Long) {
        savedLocationsDao.deleteLocation(locationId)
    }

}