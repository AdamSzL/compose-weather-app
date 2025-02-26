package com.example.weatherapp.locations.domain

import com.example.weatherapp.core.domain.Error
import com.example.weatherapp.core.presentation.UiText

enum class FetchUserLocationError: Error {
    LocationPermissionNotGranted,
    LocationServicesDisabled,
    LocationFetchFailed,
}

fun FetchUserLocationError.asUiText(): UiText {
    return when (this) {
        FetchUserLocationError.LocationFetchFailed -> UiText.DynamicString("Failed to fetch location")
        FetchUserLocationError.LocationPermissionNotGranted -> UiText.DynamicString("Location permission not granted")
        FetchUserLocationError.LocationServicesDisabled -> UiText.DynamicString("Location services disabled")
    }
}