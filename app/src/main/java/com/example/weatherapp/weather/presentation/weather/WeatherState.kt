package com.example.weatherapp.weather.presentation.weather

import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.weather.domain.WeatherHeaderInfo
import com.example.weatherapp.weather.domain.WeatherTileData
import com.example.weatherapp.weather.presentation.weather.fake.fakeWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.weather.fake.fakeWeatherTileData

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
    val error: UiText? = null
)
