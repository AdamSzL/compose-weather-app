package com.example.weatherapp.core.domain.error

import com.example.weatherapp.R
import com.example.weatherapp.core.domain.Error
import com.example.weatherapp.core.presentation.UiText
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException

enum class GetWeatherError: Error {
    NetworkError,
    ServerError,
    UnknownError
}

fun Exception.toGetWeatherError(): GetWeatherError {
    return when (this) {
        is IOException -> GetWeatherError.NetworkError
        is ClientRequestException, is ServerResponseException -> GetWeatherError.ServerError
        else -> GetWeatherError.UnknownError
    }
}

fun GetWeatherError.asUiText(): UiText {
    return when (this) {
        GetWeatherError.NetworkError -> UiText.StringResource(R.string.network_error)
        GetWeatherError.ServerError -> UiText.StringResource(R.string.server_error)
        GetWeatherError.UnknownError -> UiText.StringResource(R.string.unknown_error)
    }
}