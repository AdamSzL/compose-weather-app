package com.example.weatherapp.location_search.domain.models

import com.example.weatherapp.R
import com.example.weatherapp.core.domain.Error
import com.example.weatherapp.core.presentation.UiText

enum class LocationSearchError: Error {
    NetworkError,
    UnknownError
}

fun LocationSearchError.asUiText(): UiText {
    return when (this) {
        LocationSearchError.NetworkError -> UiText.StringResource(R.string.network_error)
        LocationSearchError.UnknownError -> UiText.StringResource(R.string.unknown_error)
    }
}