package com.example.weatherapp.locations.data.repository

import android.location.Location
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.locations.domain.models.FetchUserLocationError
import com.example.weatherapp.locations.domain.models.GeoAddress
import com.example.weatherapp.locations.domain.models.GeoLocation
import com.example.weatherapp.locations.domain.models.GeoPoint
import com.example.weatherapp.locations.domain.models.GetAddressFromLocationError
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeSavedLocations
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeUserLocation

class FakeLocationRepository(
    private val shouldReturnError: Boolean = false
): LocationRepository {

    override suspend fun getSavedLocations(): List<GeoLocation> {
        return fakeSavedLocations
    }

    override suspend fun fetchUserLocation(): Result<GeoPoint, FetchUserLocationError> {
        return if (shouldReturnError) {
            Result.Error(FetchUserLocationError.LocationPermissionNotGranted)
        } else {
            Result.Success(fakeUserLocation.coordinates)
        }
    }

    override suspend fun getAddressFromCoordinates(coordinates: GeoPoint): Result<GeoAddress, GetAddressFromLocationError> {
        for (point in fakeSavedLocations) {
            if (point.coordinates.latitude == coordinates.latitude && point.coordinates.longitude == coordinates.longitude) {
                return Result.Success(point.address)
            }
        }
        return Result.Success(GeoAddress("Unknown Location", null))
    }
}