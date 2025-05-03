package com.example.weatherapp.location_list.data.repository.location_address

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_list.domain.models.LookupAddressError

interface LocationAddressRepository {

    suspend fun lookupAddress(coordinates: GeoPoint): Result<GeoAddress, LookupAddressError>
}