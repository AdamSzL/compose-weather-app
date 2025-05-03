package com.example.weatherapp.location_search.data.repository.place_search

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_search.domain.models.FetchLocationFromPlaceIdError
import com.example.weatherapp.location_search.domain.models.LocationSearchError
import com.example.weatherapp.location_search.domain.models.PlaceSuggestion

class FakeLocationSearchRepository(
    private val shouldReturnError: Boolean = false
): LocationSearchRepository {

    override suspend fun search(query: String): Result<List<PlaceSuggestion>, LocationSearchError> {
        return Result.Error(LocationSearchError.NetworkError)
    }

    override suspend fun fetchLocationFromPlaceId(placeId: String): Result<GeoPoint, FetchLocationFromPlaceIdError> {
        return Result.Error(FetchLocationFromPlaceIdError.NetworkError)
    }
}