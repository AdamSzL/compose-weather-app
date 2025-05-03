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
        FetchUserLocationError.LocationFetchFailed -> UiText.StringResource(R.string.failed_to_fetch_location)
        FetchUserLocationError.LocationPermissionNotGranted -> UiText.StringResource(R.string.location_permission_not_granted)
        FetchUserLocationError.LocationServicesDisabled -> UiText.StringResource(R.string.location_services_disabled)
    }
}