package com.example.weatherapp.core.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.weatherapp.R
import kotlinx.serialization.Serializable

@Serializable
sealed class WeatherAppScreen(
    @StringRes val label: Int? = null,
    @DrawableRes val inactiveIcon: Int? = null,
    @DrawableRes val activeIcon: Int? = null,
    val route: String,
) {

    @Serializable
    data object WeatherScreen : WeatherAppScreen(R.string.weather, R.drawable.ic_weather, R.drawable.ic_weather_fill, "WeatherScreen")

    @Serializable
    data object ForecastScreen: WeatherAppScreen(R.string.forecast, R.drawable.ic_forecast, R.drawable.ic_forecast_fill, "ForecastScreen")

    @Serializable
    data object LocationsScreen: WeatherAppScreen(R.string.locations, R.drawable.ic_location, R.drawable.ic_location_fill, "LocationsScreen")

    @Serializable
    data object LocationMapScreen: WeatherAppScreen(route = "LocationMapScreen")

    @Serializable
    data object LocationSearchScreen: WeatherAppScreen(route = "LocationSearchScreen")

    companion object {
        fun fromRoute(route: String?): WeatherAppScreen {
            if (route == null) return WeatherScreen
            return when (route.substringAfterLast(".")) {
                WeatherScreen.route -> WeatherScreen
                ForecastScreen.route -> ForecastScreen
                LocationsScreen.route -> LocationsScreen
                LocationMapScreen.route -> LocationMapScreen
                LocationSearchScreen.route -> LocationSearchScreen
                else -> WeatherScreen
            }
        }
    }

}
