package com.example.weatherapp.location_search.data.repository.place_search

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.location_search.domain.models.LocationSearchError
import com.example.weatherapp.location_search.domain.models.PlaceSuggestion
import com.google.android.libraries.places.api.model.AutocompletePrediction

class FakeLocationSearchRepository(
    private val shouldReturnError: Boolean = false
): LocationSearchRepository {

    override suspend fun search(query: String): Result<List<PlaceSuggestion>, LocationSearchError> {
        return Result.Error(LocationSearchError.NetworkError)
//        return if (shouldReturnError) {
//        } else {
//            Result.Success(listOf("Madrid", "Barcelona"))
//        }
    }
}