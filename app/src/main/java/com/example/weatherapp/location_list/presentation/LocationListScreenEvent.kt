package com.example.weatherapp.location_list.presentation

import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.presentation.UiText

sealed interface LocationListScreenEvent {

    data object NavigateToLocationMap: LocationListScreenEvent

    data object NavigateToLocationSearch: LocationListScreenEvent

    data class NavigateToWeatherScreen(val location: GeoLocation): LocationListScreenEvent

    data class DeleteLocation(val locationId: Long): LocationListScreenEvent

    data object GoToAppSettings: LocationListScreenEvent

    data class ShowMessage(val message: UiText): LocationListScreenEvent

    data object FetchUserLocation: LocationListScreenEvent

    data object RefreshSavedLocationsWeatherBrief: LocationListScreenEvent

    data object LocationPermissionWasDenied: LocationListScreenEvent
}