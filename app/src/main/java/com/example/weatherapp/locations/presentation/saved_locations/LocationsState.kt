package com.example.weatherapp.locations.presentation.saved_locations

import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.locations.domain.models.GeoLocation
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeSavedLocations

data class LocationsState(
    val savedLocations: List<GeoLocation> = listOf(),
    val selectedLocationId: String? = null,
    val message: UiText? = null
)
