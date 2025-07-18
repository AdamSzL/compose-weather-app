package com.example.weatherapp.weather.presentation

import com.example.weatherapp.core.domain.model.DailyForecast
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.HourlyForecast
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.weather.presentation.model.WeatherHeaderInfo
import com.example.weatherapp.weather.presentation.model.WeatherTileData

data class WeatherInfo(
    val location: GeoLocation,
    val timezone: String,
    val hourlyForecast: List<HourlyForecast>,
    val dailyForecast: List<DailyForecast>,
    val weatherHeaderInfo: WeatherHeaderInfo,
    val weatherTileData: List<WeatherTileData>,
)

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val weatherTileDataHistory: List<List<WeatherTileData>> = listOf(),
    val currentWeatherTileDataIndex: Int = 0,
    val isEditModeEnabled: Boolean = false,
    val isDeleteModeEnabled: Boolean = false,
    val areTilesLocked: Boolean = false,
    val isSavingLayout: Boolean = false,
    val isLoading: Boolean = false,
    val message: UiText? = null
)
