package com.example.weatherapp.location_search.domain.use_cases

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.location_search.data.repository.place_search.LocationSearchRepository
import com.example.weatherapp.location_search.domain.models.PlaceSuggestionResultUi
import com.example.weatherapp.location_search.domain.models.asUiText

class FetchPlaceSuggestionsUseCase(
    private val locationSearchRepository: LocationSearchRepository,
) {

    suspend operator fun invoke(query: String): PlaceSuggestionResultUi {
        val placesSuggestionResultUi = when (val result = locationSearchRepository.search(query)) {
            is Result.Success -> PlaceSuggestionResultUi(result.data)
            is Result.Error -> PlaceSuggestionResultUi(emptyList(), result.error.asUiText())
        }
        return placesSuggestionResultUi
    }
}