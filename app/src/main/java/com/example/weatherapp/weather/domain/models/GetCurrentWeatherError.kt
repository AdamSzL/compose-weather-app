package com.example.weatherapp.weather.domain.models

import com.example.weatherapp.R
import com.example.weatherapp.core.domain.Error
import com.example.weatherapp.core.presentation.UiText

enum class GetCurrentWeatherError: Error {
    NetworkError
}

fun GetCurrentWeatherError.asUiText(): UiText {
    return when (this) {
        GetCurrentWeatherError.NetworkError -> UiText.StringResource(R.string.network_error)
    }
}