package com.example.weatherapp.location_list.di

import android.content.Context
import android.location.Geocoder
import com.example.weatherapp.location_list.data.repository.SavedLocationsRepository
import com.example.weatherapp.location_list.data.repository.SavedLocationsRepositoryImpl
import com.example.weatherapp.location_list.domain.use_cases.DeleteLocationUseCase
import com.example.weatherapp.location_list.domain.use_cases.ReverseGeocodeUseCase
import com.example.weatherapp.location_list.presentation.LocationListViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import java.util.Locale

val locationListModule = module {
    singleOf(::provideFusedLocationProviderClient)
    single {
        Geocoder(get(), Locale.getDefault())
    }
    single<SavedLocationsRepository> {
        SavedLocationsRepositoryImpl(get())
    }
    viewModelOf(::LocationListViewModel)
    singleOf(::DeleteLocationUseCase)
    singleOf(::ReverseGeocodeUseCase)
}

fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
    return LocationServices.getFusedLocationProviderClient(context)
}