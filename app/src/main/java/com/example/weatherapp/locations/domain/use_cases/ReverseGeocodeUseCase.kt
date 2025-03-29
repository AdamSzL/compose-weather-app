package com.example.weatherapp.locations.domain.use_cases

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.locations.data.repository.LocationRepository

class ReverseGeocodeUseCase(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(coordinates: GeoPoint): GeoLocation {
        val address = when (val addressResult = locationRepository.getAddressFromCoordinates(coordinates)) {
            is Result.Success -> {
                addressResult.data
            }
            is Result.Error -> {
                GeoAddress("Unknown Location", null)
            }
        }
        return GeoLocation(
            coordinates = coordinates,
            address = address
        )
    }
}