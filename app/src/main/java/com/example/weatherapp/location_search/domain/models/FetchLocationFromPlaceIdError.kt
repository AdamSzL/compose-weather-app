package com.example.weatherapp.location_search.domain.models

import com.example.weatherapp.R
import com.example.weatherapp.core.domain.Error
import com.example.weatherapp.core.presentation.UiText

enum class FetchLocationFromPlaceIdError: Error {
    NetworkError,
    NoLocationDataError,
    UnknownError,
}

fun FetchLocationFromPlaceIdError.asUiText(): UiText {
    return when (this) {
        FetchLocationFromPlaceIdError.NetworkError -> UiText.StringResource(R.string.network_error)
        FetchLocationFromPlaceIdError.NoLocationDataError -> UiText.StringResource(R.string.no_location_data_error)
        FetchLocationFromPlaceIdError.UnknownError -> UiText.StringResource(R.string.unknown_error)
    }
}