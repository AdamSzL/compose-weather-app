package com.example.weatherapp.locations.data.repository

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.locations.domain.models.FetchUserLocationError
import com.example.weatherapp.locations.domain.models.GetAddressFromLocationError
import com.example.weatherapp.locations.domain.models.LocationWeatherBrief

interface LocationRepository {

    suspend fun getSavedLocations(): List<LocationWeatherBrief>

    suspend fun fetchUserLocation(): Result<GeoPoint, FetchUserLocationError>

    suspend fun getAddressFromCoordinates(coordinates: GeoPoint): Result<GeoAddress, GetAddressFromLocationError>

}