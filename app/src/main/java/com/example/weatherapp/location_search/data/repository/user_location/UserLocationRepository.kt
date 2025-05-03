package com.example.weatherapp.location_search.data.repository.user_location

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_search.domain.models.FetchUserLocationError

interface UserLocationRepository {
    suspend fun getUserLocation(): Result<GeoPoint, FetchUserLocationError>
}