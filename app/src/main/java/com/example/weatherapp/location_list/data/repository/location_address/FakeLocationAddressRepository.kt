package com.example.weatherapp.location_list.data.repository.location_address

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.fake.fakeLocations
import com.example.weatherapp.location_list.domain.models.LookupAddressError

class FakeLocationAddressRepository: LocationAddressRepository {

    override suspend fun lookupAddress(coordinates: GeoPoint): Result<GeoAddress, LookupAddressError> {
        for (location in fakeLocations) {
            if (location.coordinates.latitude == coordinates.latitude && location.coordinates.longitude == coordinates.longitude) {
                return Result.Success(location.address)
            }
        }
        return Result.Success(GeoAddress("Unknown Location", null))
    }

}