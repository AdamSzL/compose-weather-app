package com.example.weatherapp.weather.presentation

import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.weather.presentation.model.WeatherHeaderInfo
import com.example.weatherapp.weather.presentation.model.WeatherTileData
import com.example.weatherapp.weather.presentation.fake.fakeWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.fake.fakeWeatherTileData

data class WeatherState(
    val weatherHeaderInfo: WeatherHeaderInfo? = fakeWeatherHeaderInfo,
    val weatherTileData: List<WeatherTileData> = fakeWeatherTileData,
    val weatherTileDataHistory: List<List<WeatherTileData>> = listOf(fakeWeatherTileData),
    val currentWeatherTileDataIndex: Int = 0,
    val isEditModeEnabled: Boolean = false,
    val isDeleteModeEnabled: Boolean = false,
    val areTilesLocked: Boolean = false,
    val isAutoSaveEnabled: Boolean = true,
    val isSavingLayout: Boolean = false,
    val isLoading: Boolean = false,
    val message: UiText? = null
)
