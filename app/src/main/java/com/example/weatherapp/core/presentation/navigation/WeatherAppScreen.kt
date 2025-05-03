package com.example.weatherapp.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface WeatherAppScreen {

    @Serializable
    data class WeatherScreen(val locationId: Long)

    @Serializable
    data object ForecastScreen: WeatherAppScreen

    @Serializable
    data object LocationListScreen: WeatherAppScreen

    @Serializable
    data object LocationMapScreen: WeatherAppScreen

    @Serializable
    data object LocationSearchScreen: WeatherAppScreen

}
