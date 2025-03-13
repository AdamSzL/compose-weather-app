package com.example.weatherapp.weather.di

import com.example.weatherapp.weather.data.repository.FakeWeatherRepository
import com.example.weatherapp.weather.data.repository.WeatherRepository
import com.example.weatherapp.weather.domain.use_cases.DeleteTileUseCase
import com.example.weatherapp.weather.domain.use_cases.MoveTileUseCase
import com.example.weatherapp.weather.domain.use_cases.ResetLayoutUseCase
import com.example.weatherapp.weather.domain.use_cases.SaveLayoutInHistoryUseCase
import com.example.weatherapp.weather.presentation.forecast.ForecastViewModel
import com.example.weatherapp.weather.presentation.weather.WeatherViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weatherModule = module {
    viewModelOf(::WeatherViewModel)
    viewModelOf(::ForecastViewModel)
    singleOf(::DeleteTileUseCase)
    singleOf(::MoveTileUseCase)
    singleOf(::SaveLayoutInHistoryUseCase)
    singleOf(::ResetLayoutUseCase)
    single<WeatherRepository> {
        FakeWeatherRepository()
    }

}