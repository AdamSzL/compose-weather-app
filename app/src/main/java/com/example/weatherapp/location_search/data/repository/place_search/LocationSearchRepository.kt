package com.example.weatherapp.location_search.data.repository.place_search

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_search.domain.models.FetchLocationFromPlaceIdError
import com.example.weatherapp.location_search.domain.models.LocationSearchError
import com.example.weatherapp.location_search.domain.models.PlaceSuggestion

interface LocationSearchRepository {

    suspend fun search(query: String): Result<List<PlaceSuggestion>, LocationSearchError>

    suspend fun fetchLocationFromPlaceId(placeId: String): Result<GeoPoint, FetchLocationFromPlaceIdError>
}