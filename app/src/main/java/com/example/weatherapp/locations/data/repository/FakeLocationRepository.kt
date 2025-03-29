package com.example.weatherapp.locations.data.repository

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.locations.domain.models.FetchUserLocationError
import com.example.weatherapp.locations.domain.models.GetAddressFromLocationError
import com.example.weatherapp.locations.domain.models.LocationWeatherBrief
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeLocations
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeUserLocation

class FakeLocationRepository(
    private val shouldReturnError: Boolean = false
): LocationRepository {

    override suspend fun getSavedLocations(): List<LocationWeatherBrief> {
        return fakeLocations
    }

    override suspend fun fetchUserLocation(): Result<GeoPoint, FetchUserLocationError> {
        return if (shouldReturnError) {
            Result.Error(FetchUserLocationError.LocationPermissionNotGranted)
        } else {
            Result.Success(fakeUserLocation.location.coordinates)
        }
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