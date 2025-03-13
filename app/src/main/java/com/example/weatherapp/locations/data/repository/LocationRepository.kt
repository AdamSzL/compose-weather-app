package com.example.weatherapp.locations.data.repository

import android.location.Location
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.locations.domain.models.FetchUserLocationError
import com.example.weatherapp.locations.domain.models.GeoAddress
import com.example.weatherapp.locations.domain.models.GeoLocation
import com.example.weatherapp.locations.domain.models.GeoPoint
import com.example.weatherapp.locations.domain.models.GetAddressFromLocationError

interface LocationRepository {

    suspend fun getSavedLocations(): List<GeoLocation>

    suspend fun fetchUserLocation(): Result<GeoPoint, FetchUserLocationError>

    suspend fun getAddressFromCoordinates(coordinates: GeoPoint): Result<GeoAddress, GetAddressFromLocationError>

}