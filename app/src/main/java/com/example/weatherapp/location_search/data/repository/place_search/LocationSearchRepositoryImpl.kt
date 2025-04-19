package com.example.weatherapp.location_search.data.repository.place_search

import android.util.Log
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.location_search.domain.models.LocationSearchError
import com.example.weatherapp.location_search.domain.models.PlaceSuggestion
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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
}