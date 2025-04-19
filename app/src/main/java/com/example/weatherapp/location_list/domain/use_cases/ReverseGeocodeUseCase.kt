package com.example.weatherapp.location_list.domain.use_cases

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_list.data.repository.SavedLocationsRepository

class ReverseGeocodeUseCase(
    private val savedLocationsRepository: SavedLocationsRepository
) {
    suspend operator fun invoke(coordinates: GeoPoint): GeoLocation {
        val address = when (val addressResult = savedLocationsRepository.getAddressFromCoordinates(coordinates)) {
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