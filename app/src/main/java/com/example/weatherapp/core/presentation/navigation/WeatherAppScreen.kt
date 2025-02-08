package com.example.weatherapp.core.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.weatherapp.R

enum class WeatherAppScreen(
    @StringRes val label: Int,
    @DrawableRes val inactiveIcon: Int,
    @DrawableRes val activeIcon: Int,
) {
    WeatherScreen(R.string.weather, R.drawable.ic_weather, R.drawable.ic_weather_fill),
    ForecastScreen(R.string.forecast, R.drawable.ic_forecast, R.drawable.ic_forecast_fill),
    LocationsScreen(R.string.locations, R.drawable.ic_location, R.drawable.ic_location_fill)
}