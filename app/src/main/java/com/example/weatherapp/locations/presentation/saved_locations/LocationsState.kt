package com.example.weatherapp.locations.presentation.saved_locations

import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.locations.domain.models.LocationWeatherBrief

data class LocationsState(
    val locations: List<LocationWeatherBrief> = listOf(),
    val message: UiText? = null
)
