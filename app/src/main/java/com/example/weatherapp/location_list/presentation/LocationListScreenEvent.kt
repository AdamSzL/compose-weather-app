package com.example.weatherapp.location_list.presentation

import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.presentation.UiText

sealed interface LocationListScreenEvent {

    data object NavigateToLocationMap: LocationListScreenEvent

    data object NavigateToLocationSearch: LocationListScreenEvent

    data class NavigateToWeatherScreen(val location: GeoLocation): LocationListScreenEvent

    data class AddMapLocation(val coordinates: GeoPoint): LocationListScreenEvent

    data class DeleteLocation(val locationId: String): LocationListScreenEvent

    data object GoToAppSettings: LocationListScreenEvent

    data class ShowSnackbar(val message: UiText): LocationListScreenEvent

    data object ResetMessage: LocationListScreenEvent

    data object ResetSavedMapLocation: LocationListScreenEvent

    data object FetchUserLocation: LocationListScreenEvent
}