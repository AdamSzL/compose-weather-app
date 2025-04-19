package com.example.weatherapp.location_list.presentation

import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.location_list.domain.models.LocationWeatherBrief

data class LocationListState(
    val locations: List<LocationWeatherBrief> = listOf(),
    val message: UiText? = null
)
