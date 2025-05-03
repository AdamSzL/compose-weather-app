package com.example.weatherapp.location_search.data.repository.place_search

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_search.domain.models.FetchLocationFromPlaceIdError
import com.example.weatherapp.location_search.domain.models.LocationSearchError
import com.example.weatherapp.location_search.domain.models.PlaceSuggestion
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.kotlin.awaitFetchPlace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.io.IOException

class LocationSearchRepositoryImpl(
    private val placesClient: PlacesClient
): LocationSearchRepository {

    private var sessionToken: AutocompleteSessionToken? = null

    override suspend fun search(query: String): Result<List<PlaceSuggestion>, LocationSearchError> {
        if (sessionToken == null) {
            sessionToken = AutocompleteSessionToken.newInstance()
        }

        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .setSessionToken(sessionToken)
            .build()

        return try {
            val result = withContext(Dispatchers.IO) {
                placesClient.findAutocompletePredictions(request).await()
            }

            val suggestions = result.autocompletePredictions.map {
                PlaceSuggestion(
                    placeId = it.placeId,
                    primaryText = it.getPrimaryText(null).toString(),
                    secondaryText = it.getSecondaryText(null).toString()
                )
            }
            Result.Success(suggestions)
        } catch (e: Exception) {
            Result.Error(LocationSearchError.UnknownError)
        }
    }

    override suspend fun fetchLocationFromPlaceId(placeId: String): Result<GeoPoint, FetchLocationFromPlaceIdError> {
        try {
            val placeFields = listOf(Place.Field.LOCATION)
            val response = placesClient.awaitFetchPlace(placeId, placeFields)
            val latLng = response.place.location
                ?: return Result.Error(FetchLocationFromPlaceIdError.NoLocationDataError)
            val location = GeoPoint(
                latitude = latLng.latitude,
                longitude = latLng.longitude
            )
            return Result.Success(location)
        } catch (e: IOException) {
            return Result.Error(FetchLocationFromPlaceIdError.NetworkError)
        } catch (e: Exception) {
            return Result.Error(FetchLocationFromPlaceIdError.UnknownError)
        }
    }
}