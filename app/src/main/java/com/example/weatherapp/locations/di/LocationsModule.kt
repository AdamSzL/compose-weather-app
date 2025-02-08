package com.example.weatherapp.locations.di

import com.example.weatherapp.locations.presentation.LocationsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val locationsModule = module {
    viewModelOf(::LocationsViewModel)
}