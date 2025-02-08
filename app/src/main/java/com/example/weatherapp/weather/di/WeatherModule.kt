package com.example.weatherapp.weather.di

import com.example.weatherapp.weather.presentation.forecast.ForecastViewModel
import com.example.weatherapp.weather.presentation.weather.WeatherViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weatherModule = module {
    viewModelOf(::WeatherViewModel)
    viewModelOf(::ForecastViewModel)
}