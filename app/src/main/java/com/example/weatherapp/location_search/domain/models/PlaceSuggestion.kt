package com.example.weatherapp.location_search.domain.models

data class PlaceSuggestion(
    val placeId: String,
    val primaryText: String,
    val secondaryText: String?
)
