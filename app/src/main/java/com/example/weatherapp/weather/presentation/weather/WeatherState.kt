package com.example.weatherapp.weather.presentation.weather

import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.weather.domain.WeatherInfo
import com.example.weatherapp.weather.presentation.weather.mock.mockWeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = mockWeatherInfo,
    val isLoading: Boolean = false,
    val error: UiText? = null
)
