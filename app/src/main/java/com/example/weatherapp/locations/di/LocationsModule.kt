package com.example.weatherapp.locations.di

import android.content.Context
import android.location.Geocoder
import com.example.weatherapp.locations.data.repository.LocationRepository
import com.example.weatherapp.locations.data.repository.LocationRepositoryImpl
import com.example.weatherapp.locations.domain.use_cases.DeleteLocationUseCase
import com.example.weatherapp.locations.domain.use_cases.ReverseGeocodeUseCase
import com.example.weatherapp.locations.presentation.saved_locations.LocationsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import java.util.Locale

val locationsModule = module {
    singleOf(::provideFusedLocationProviderClient)
    single {
        Geocoder(get(), Locale.getDefault())
    }
    single<LocationRepository> {
        LocationRepositoryImpl(get())
    }
    viewModelOf(::LocationsViewModel)
    singleOf(::DeleteLocationUseCase)
    singleOf(::ReverseGeocodeUseCase)
}

fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
    return LocationServices.getFusedLocationProviderClient(context)
}