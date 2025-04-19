package com.example.weatherapp.location_search.data.repository.user_location

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_search.domain.models.FetchUserLocationError
import com.example.weatherapp.location_list.presentation.fake.fakeUserLocation

class FakeUserLocationRepository(
    private val shouldReturnError: Boolean = false
): UserLocationRepository {

    override suspend fun getUserLocation(): Result<GeoPoint, FetchUserLocationError> {
        return if (shouldReturnError) {
            Result.Error(FetchUserLocationError.LocationPermissionNotGranted)
        } else {
            Result.Success(fakeUserLocation.location.coordinates)
        }
    }
}