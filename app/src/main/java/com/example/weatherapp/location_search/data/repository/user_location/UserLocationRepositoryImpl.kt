package com.example.weatherapp.location_search.data.repository.user_location

import android.annotation.SuppressLint
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_search.domain.models.FetchUserLocationError

class UserLocationRepositoryImpl(): UserLocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getUserLocation(): Result<GeoPoint, FetchUserLocationError> {
//        return try {
//            val location = fusedLocationClient.lastLocation.await()
//            Result.Success(location)
//        } catch (e: Exception) {
//            Log.d("XXX", e.toString())
//            Result.Error(FetchUserLocationError.LocationFetchFailed)
//        }
        return Result.Error(FetchUserLocationError.LocationFetchFailed)
    }
}