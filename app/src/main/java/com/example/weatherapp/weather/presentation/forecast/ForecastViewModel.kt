package com.example.weatherapp.weather.presentation.forecast

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ForecastViewModel(

): ViewModel() {

    private val _forecastState = MutableStateFlow(ForecastState())
    val forecastState = _forecastState.asStateFlow()

    fun onForecastScreenEvent(forecastScreenEvent: ForecastScreenEvent) {
        when (forecastScreenEvent) {
            else -> Unit
        }
    }
}