package com.example.weatherapp.locations.di

import android.content.Context
import com.example.weatherapp.locations.data.repository.FakeLocationRepository
import com.example.weatherapp.locations.data.repository.LocationRepository
import com.example.weatherapp.locations.domain.use_cases.DeleteLocationUseCase
import com.example.weatherapp.locations.domain.use_cases.ReverseGeocodeUseCase
import com.example.weatherapp.locations.presentation.saved_locations.LocationsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val locationsModule = module {
    singleOf(::provideFusedLocationProviderClient)
    single<LocationRepository> {
        FakeLocationRepository()
    }
    viewModelOf(::LocationsViewModel)
    singleOf(::DeleteLocationUseCase)
    singleOf(::ReverseGeocodeUseCase)
}

fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
    return LocationServices.getFusedLocationProviderClient(context)
}