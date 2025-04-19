package com.example.weatherapp.location_list.data.repository

import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_list.domain.models.GetAddressFromLocationError
import com.example.weatherapp.location_list.domain.models.LocationWeatherBrief
import com.example.weatherapp.location_list.presentation.fake.fakeLocations
import java.io.IOException

class SavedLocationsRepositoryImpl(
    private val geocoder: Geocoder,
//    private val fusedLocationClient: FusedLocationProviderClient
): SavedLocationsRepository {

    override suspend fun getSavedLocations(): List<LocationWeatherBrief> {
        return fakeLocations
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun getAddressFromCoordinates(coordinates: GeoPoint): Result<GeoAddress, GetAddressFromLocationError> {
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
            Result.Error(GetAddressFromLocationError.NetworkError)
        } catch (e: Exception) {
            Result.Error(GetAddressFromLocationError.Unknown)
        }
    }

}