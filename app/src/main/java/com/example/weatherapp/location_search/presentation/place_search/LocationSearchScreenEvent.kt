package com.example.weatherapp.location_search.presentation.place_search

sealed interface LocationSearchScreenEvent {

    data object NavigateBack: LocationSearchScreenEvent

    data class LocationSearchQueryChanged(val query: String): LocationSearchScreenEvent

    data class PlaceSelected(val placeId: String): LocationSearchScreenEvent
}