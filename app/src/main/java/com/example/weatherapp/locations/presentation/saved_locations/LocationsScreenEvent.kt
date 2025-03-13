package com.example.weatherapp.locations.presentation.saved_locations

import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.locations.domain.models.GeoPoint

sealed class LocationsScreenEvent {

    data object NavigateToLocationMap: LocationsScreenEvent()

    data object NavigateToLocationSearch: LocationsScreenEvent()

    data class AddMapLocation(val coordinates: GeoPoint): LocationsScreenEvent()

    data class DeleteLocation(val locationId: String): LocationsScreenEvent()

    data class SetLocationAsActive(val locationId: String): LocationsScreenEvent()

    data object GoToAppSettings: LocationsScreenEvent()

    data class ShowSnackbar(val message: UiText): LocationsScreenEvent()

    data object ResetMessage: LocationsScreenEvent()

    data object ResetSavedMapLocation: LocationsScreenEvent()

    data object FetchUserLocation: LocationsScreenEvent()
}