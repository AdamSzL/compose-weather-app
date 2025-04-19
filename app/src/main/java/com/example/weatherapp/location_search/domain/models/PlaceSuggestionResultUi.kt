package com.example.weatherapp.location_search.domain.models

import com.example.weatherapp.core.presentation.UiText

data class PlaceSuggestionResultUi(
    val placesSuggestions: List<PlaceSuggestion>,
    val message: UiText? = null,
)
