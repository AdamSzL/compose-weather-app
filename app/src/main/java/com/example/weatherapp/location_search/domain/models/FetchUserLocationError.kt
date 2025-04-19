package com.example.weatherapp.location_search.domain.models

import com.example.weatherapp.R
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
        FetchUserLocationError.LocationPermissionNotGranted -> UiText.StringResource(R.string.location_permission_not_granted)
        FetchUserLocationError.LocationServicesDisabled -> UiText.DynamicString("Location services disabled")
    }
}