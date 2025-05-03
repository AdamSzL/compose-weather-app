package com.example.weatherapp.location_list.data.repository.location_address

import android.location.Geocoder
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_list.domain.models.LookupAddressError
import java.io.IOException

class LocationAddressRepositoryImpl(
    private val geocoder: Geocoder
): LocationAddressRepository {

    override suspend fun lookupAddress(coordinates: GeoPoint): Result<GeoAddress, LookupAddressError> {
        return try {
            val addresses = geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1)

            if (addresses.isNullOrEmpty()) {
                Result.Success(GeoAddress(name = "Unknown location", null))
            } else {
                val address = addresses.first()
                val name = address.locality ?: address.subAdminArea ?: address.adminArea ?: address.featureName ?: "Unknown location"
                val country = address.countryName
                Result.Success(GeoAddress(name = name, country = country))
            }
        } catch (e: IOException) {
            Result.Error(LookupAddressError.NetworkError)
        } catch (e: Exception) {
            Result.Error(LookupAddressError.Unknown)
        }
    }

}