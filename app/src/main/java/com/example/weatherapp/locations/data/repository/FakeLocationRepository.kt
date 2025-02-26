package com.example.weatherapp.locations.data.repository

import android.location.Location
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.locations.domain.FetchUserLocationError
import com.example.weatherapp.locations.domain.GetAddressFromLocationError
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay

class FakeLocationRepository(

): LocationRepository {

    private val mockAddresses = mapOf(
        LatLng(40.7128, -74.0060) to Pair("New York City", "USA"),
        LatLng(48.8566, 2.3522) to Pair("Paris", "France"),
        LatLng(-33.8688, 151.2093) to Pair("Sydney", "Australia"),
        LatLng(35.6895, 139.6917) to Pair("Tokyo", "Japan"),
        LatLng(-34.6037, -58.3816) to Pair("Buenos Aires", "Argentina")
    )

    override suspend fun fetchUserLocation(): Result<Location, FetchUserLocationError> {
        val fakeLocation = Location("mock").apply {
            latitude = 40.7128
            longitude = -74.0060
        }

        return Result.Success(fakeLocation)
    }

    override suspend fun getAddressFromLocation(location: Location): Result<Pair<String, String?>, GetAddressFromLocationError> {
        for (latLng in mockAddresses.keys) {
            if (latLng.latitude == location.latitude && latLng.longitude == location.longitude) {
                val address = mockAddresses[latLng]!!
                return Result.Success(address)
            }
        }
        return Result.Success(Pair("Unknown Location", null))
    }
}