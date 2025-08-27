package com.example.weatherapp.weather.di

import com.example.weatherapp.weather.data.repository.TileLayoutRepository
import com.example.weatherapp.weather.data.repository.TileLayoutRepositoryImpl
import com.example.weatherapp.weather.domain.use_cases.DeleteTileUseCase
import com.example.weatherapp.weather.domain.use_cases.MoveTileUseCase
import com.example.weatherapp.weather.domain.use_cases.SaveLayoutInHistoryUseCase
import com.example.weatherapp.weather.presentation.WeatherViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weatherModule = module {
    viewModelOf(::WeatherViewModel)
    singleOf(::DeleteTileUseCase)
    singleOf(::MoveTileUseCase)
    singleOf(::SaveLayoutInHistoryUseCase)

    single<TileLayoutRepository> {
        TileLayoutRepositoryImpl(get())
    }
}