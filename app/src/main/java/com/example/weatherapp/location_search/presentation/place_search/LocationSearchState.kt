package com.example.weatherapp.location_search.presentation.place_search

import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.location_search.domain.models.PlaceSuggestion

data class LocationSearchState(
    val message: UiText? = null,
    val locationSearchQuery: String = "",
    val isLoadingSuggestions: Boolean = false,
    val placeSuggestions: List<PlaceSuggestion> = emptyList()
)