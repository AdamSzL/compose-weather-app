package com.example.weatherapp.location_search.data.repository.user_location

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_search.domain.models.FetchUserLocationError
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await

class UserLocationRepositoryImpl(
    private val fusedLocationClient: FusedLocationProviderClient
): UserLocationRepository {

    override suspend fun getUserLocation(): Result<GeoPoint, FetchUserLocationError> {
        return try {
            val location = fusedLocationClient.lastLocation.await()
                ?: return Result.Error(FetchUserLocationError.LocationServicesDisabled)

            val point = GeoPoint(
                longitude = location.longitude,
                latitude = location.latitude,
            )
            Result.Success(point)
        } catch (e: SecurityException) {
            Result.Error(FetchUserLocationError.LocationPermissionNotGranted)
        } catch (e: Exception) {
            Result.Error(FetchUserLocationError.LocationFetchFailed)
        }
    }
}