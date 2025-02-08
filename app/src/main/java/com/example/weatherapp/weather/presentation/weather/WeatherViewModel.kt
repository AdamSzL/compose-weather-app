package com.example.weatherapp.weather.presentation.weather

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.text.Typography.dagger

class WeatherViewModel(

): ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState = _weatherState.asStateFlow()

    fun onWeatherScreenEvent(weatherScreenEvent: WeatherScreenEvent) {
        when (weatherScreenEvent) {
            else -> Unit
        }
    }
}