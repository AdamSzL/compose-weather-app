package com.example.weatherapp.location_list.presentation

import com.example.weatherapp.core.domain.UiEvent
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.location_list.domain.models.LocationWeatherBrief

data class LocationListState(
    val locationsWithWeatherBrief: List<LocationWeatherBrief> = listOf(),
    val isRefreshingWeatherBriefs: Boolean = false,
    val showMessageEvent: UiEvent<UiText>? = null,
    val wasLocationPermissionAlreadyDenied: Boolean = false
)
