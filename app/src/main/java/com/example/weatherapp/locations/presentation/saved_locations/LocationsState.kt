package com.example.weatherapp.locations.presentation.saved_locations

import com.example.weatherapp.locations.domain.SavedLocation
import com.example.weatherapp.locations.presentation.saved_locations.mock.mockSavedLocations

data class LocationsState(
    val savedLocations: List<SavedLocation> = mockSavedLocations,
    val selectedLocation: SavedLocation? = mockSavedLocations.first(),
)
