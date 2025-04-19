package com.example.weatherapp.location_search.data.repository.place_search

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.location_search.domain.models.LocationSearchError
import com.example.weatherapp.location_search.domain.models.PlaceSuggestion
import com.google.android.libraries.places.api.model.AutocompletePrediction

interface LocationSearchRepository {

    suspend fun search(query: String): Result<List<PlaceSuggestion>, LocationSearchError>
}