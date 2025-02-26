package com.example.weatherapp.locations.presentation.saved_locations

import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.locations.domain.SavedLocation
import com.example.weatherapp.locations.presentation.saved_locations.mock.mockSavedLocations

data class LocationsState(
    val savedLocations: List<SavedLocation> = mockSavedLocations.take(5),
    val selectedLocationId: String? = mockSavedLocations.first().id,
    val message: UiText? = null
)
