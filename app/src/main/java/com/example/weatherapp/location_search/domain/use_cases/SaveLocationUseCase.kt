package com.example.weatherapp.location_search.domain.use_cases

import android.util.Log
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_list.data.repository.location_address.LocationAddressRepository
import com.example.weatherapp.location_list.data.repository.saved_locations.SavedLocationsRepository
import com.example.weatherapp.location_search.domain.models.SaveLocationError

class SaveLocationUseCase(
    private val savedLocationsRepository: SavedLocationsRepository,
    private val locationAddressRepository: LocationAddressRepository,
) {

    suspend operator fun invoke(point: GeoPoint): Result<Unit, SaveLocationError> {
        val addressResult = locationAddressRepository.lookupAddress(point)
        if (addressResult is Result.Error) {
            return Result.Error(SaveLocationError.FailedToFetchAddress)
        }
        val location = GeoLocation(
            id = 0,
            coordinates = point,
            address = (addressResult as Result.Success).data,
        )
        savedLocationsRepository.insertLocation(location)
        return Result.Success(Unit)
    }
}