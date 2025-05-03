package com.example.weatherapp.location_search.presentation.place_search

import com.example.weatherapp.core.domain.UiEvent
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.location_search.domain.models.PlaceSuggestion

data class LocationSearchState(
    val showMessageEvent: UiEvent<UiText>? = null,
    val locationSearchQuery: String = "",
    val isLoadingSuggestions: Boolean = false,
    val placeSuggestions: List<PlaceSuggestion> = emptyList(),
    val navigateBackEvent: UiEvent<Unit>? = null,
    val currentlySavingPlaceId: String? = null
)