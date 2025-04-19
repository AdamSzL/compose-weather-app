package com.example.weatherapp.location_list.data.repository

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_list.domain.models.GetAddressFromLocationError
import com.example.weatherapp.location_list.domain.models.LocationWeatherBrief

interface SavedLocationsRepository {

    suspend fun getSavedLocations(): List<LocationWeatherBrief>

    suspend fun getAddressFromCoordinates(coordinates: GeoPoint): Result<GeoAddress, GetAddressFromLocationError>

    // suspend fun addLocation()

    // suspend fun removeLocation()

}