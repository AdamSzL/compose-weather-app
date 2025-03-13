package com.example.weatherapp.locations.data.repository

import android.annotation.SuppressLint
import android.location.Location
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.locations.domain.models.FetchUserLocationError
import com.example.weatherapp.locations.domain.models.GeoAddress
import com.example.weatherapp.locations.domain.models.GeoLocation
import com.example.weatherapp.locations.domain.models.GeoPoint
import com.example.weatherapp.locations.domain.models.GetAddressFromLocationError
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await

class DefaultLocationRepository(
//    private val geocoder: Geocoder,
//    private val fusedLocationClient: FusedLocationProviderClient
): LocationRepository {

    override suspend fun getSavedLocations(): List<GeoLocation> {
        return listOf()
    }

    @SuppressLint("MissingPermission")
    override suspend fun fetchUserLocation(): Result<GeoPoint, FetchUserLocationError> {
//        return try {
//            val location = fusedLocationClient.lastLocation.await()
//            Result.Success(location)
//        } catch (e: Exception) {
//            Log.d("XXX", e.toString())
//            Result.Error(FetchUserLocationError.LocationFetchFailed)
//        }
        return Result.Error(FetchUserLocationError.LocationFetchFailed)
    }

    override suspend fun getAddressFromCoordinates(coordinates: GeoPoint): Result<GeoAddress, GetAddressFromLocationError> {
//        return try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                suspendCancellableCoroutine { continuation ->
//                    geocoder.getFromLocation(location.latitude, location.longitude, 1, object: GeocodeListener {
//                        override fun onGeocode(addresses: MutableList<Address>) {
//                            val address = addresses.firstOrNull()?.getAddressLine(0) ?: "Unknown Location"
//                            continuation.resume(Result.Success(address)) { cause -> {
//                                Log.d("XXX", cause.toString())
//                            }}
//                        }
//
//                        override fun onError(errorMessage: String?) {
//                            continuation.resumeWithException(Exception(errorMessage ?: "Unknown"))
//                        }
//                    })
//                }
//            } else {
//                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//                val address = addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown Location"
//                Result.Success(address)
//            }
//        } catch (e: Exception) {
//            Log.d("XXX", e.toString())
//            Result.Error(GetAddressFromLocationError.AddressFetchFailed)
//        }
        return Result.Success(GeoAddress("Unknown Location", null))
    }

}