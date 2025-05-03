package com.example.weatherapp.location_search.domain.models

import com.example.weatherapp.R
import com.example.weatherapp.core.domain.Error
import com.example.weatherapp.core.presentation.UiText

enum class SaveLocationError: Error {
    FailedToFetchAddress,
    UnknownError
}

fun SaveLocationError.asUiText(): UiText {
    return when (this) {
        SaveLocationError.FailedToFetchAddress -> UiText.StringResource(R.string.failed_to_fetch_address)
        SaveLocationError.UnknownError -> UiText.StringResource(R.string.unknown_error)
    }
}