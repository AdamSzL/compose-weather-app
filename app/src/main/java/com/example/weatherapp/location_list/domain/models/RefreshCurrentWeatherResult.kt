package com.example.weatherapp.location_list.domain.models

import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.UiText

sealed interface RefreshCurrentWeatherResult {
    data object Error: RefreshCurrentWeatherResult
    data object Updated: RefreshCurrentWeatherResult
    data object UpToDate: RefreshCurrentWeatherResult
}

fun RefreshCurrentWeatherResult.asUiText(): UiText {
    return when (this) {
        is RefreshCurrentWeatherResult.Error -> UiText.StringResource(R.string.failed_to_refresh_weather)
        is RefreshCurrentWeatherResult.Updated -> UiText.StringResource(R.string.weather_updated)
        is RefreshCurrentWeatherResult.UpToDate -> UiText.StringResource(R.string.all_locations_up_to_date)
    }
}