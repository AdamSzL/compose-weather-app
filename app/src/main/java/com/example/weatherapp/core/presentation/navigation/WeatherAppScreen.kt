package com.example.weatherapp.core.presentation.navigation

import com.example.weatherapp.core.domain.model.GeoLocation
import kotlinx.serialization.Serializable

@Serializable
sealed interface WeatherAppScreen {

    @Serializable
    data class WeatherScreen(val location: GeoLocation)

    @Serializable
    data object ForecastScreen: WeatherAppScreen

    @Serializable
    data object LocationsScreen: WeatherAppScreen

    @Serializable
    data object LocationMapScreen: WeatherAppScreen

    @Serializable
    data object LocationSearchScreen: WeatherAppScreen

}
