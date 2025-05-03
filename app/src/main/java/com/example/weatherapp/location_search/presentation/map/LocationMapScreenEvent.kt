package com.example.weatherapp.location_search.presentation.map

import com.example.weatherapp.core.domain.model.GeoPoint

sealed interface LocationMapScreenEvent {

    data object NavigateBack: LocationMapScreenEvent

    data class SelectLocation(val location: GeoPoint): LocationMapScreenEvent

    data object SaveSelectedLocation: LocationMapScreenEvent
}