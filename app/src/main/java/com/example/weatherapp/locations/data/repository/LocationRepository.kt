package com.example.weatherapp.locations.data.repository

import android.location.Location
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.locations.domain.FetchUserLocationError
import com.example.weatherapp.locations.domain.GetAddressFromLocationError

interface LocationRepository {

    suspend fun fetchUserLocation(): Result<Location, FetchUserLocationError>

    suspend fun getAddressFromLocation(location: Location): Result<Pair<String, String?>, GetAddressFromLocationError>

}