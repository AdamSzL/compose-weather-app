package com.example.weatherapp.location_list.data.repository

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_list.domain.models.GetAddressFromLocationError
import com.example.weatherapp.location_list.domain.models.LocationWeatherBrief
import com.example.weatherapp.location_list.presentation.fake.fakeLocations

class FakeSavedLocationsRepository(
    private val shouldReturnError: Boolean = false
): SavedLocationsRepository {

    override suspend fun getSavedLocations(): List<LocationWeatherBrief> {
        return fakeLocations
    }

    override suspend fun getAddressFromCoordinates(coordinates: GeoPoint): Result<GeoAddress, GetAddressFromLocationError> {
        val locations = fakeLocations.map { it.location }
        for (location in locations) {
            if (location.coordinates.latitude == coordinates.latitude && location.coordinates.longitude == coordinates.longitude) {
                return Result.Success(location.address)
            }
        }
        return Result.Success(GeoAddress("Unknown Location", null))
    }
}