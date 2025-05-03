package com.example.weatherapp.location_search.presentation.map

import com.example.weatherapp.core.domain.UiEvent
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.presentation.UiText

data class LocationMapState(
    val showMessageEvent: UiEvent<UiText>? = null,
    val isSavingLocation: Boolean = false,
    val selectedLocation: GeoPoint? = null,
    val navigateBackEvent: UiEvent<Unit>? = null
)