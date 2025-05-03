package com.example.weatherapp.location_search.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.weatherapp.location_search.data.repository.location_permission.LocationPermissionRepository
import com.example.weatherapp.location_search.data.repository.location_permission.LocationPermissionRepositoryImpl
import com.example.weatherapp.location_search.data.repository.place_search.LocationSearchRepository
import com.example.weatherapp.location_search.data.repository.place_search.LocationSearchRepositoryImpl
import com.example.weatherapp.location_search.data.repository.user_location.UserLocationRepository
import com.example.weatherapp.location_search.data.repository.user_location.UserLocationRepositoryImpl
import com.example.weatherapp.location_search.data.utils.dataStore
import com.example.weatherapp.location_search.domain.use_cases.FetchPlaceSuggestionsUseCase
import com.example.weatherapp.location_search.domain.use_cases.SaveLocationUseCase
import com.example.weatherapp.location_search.presentation.map.LocationMapViewModel
import com.example.weatherapp.location_search.presentation.place_search.LocationSearchViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val locationSearchModule = module {

    single<DataStore<Preferences>> {
        androidContext().dataStore
    }

    single<UserLocationRepository> {
        UserLocationRepositoryImpl(get())
    }
    single<LocationSearchRepository> {
        LocationSearchRepositoryImpl(get())
    }
    single<LocationPermissionRepository> {
        LocationPermissionRepositoryImpl(get())
    }

    viewModelOf(::LocationMapViewModel)
    viewModelOf(::LocationSearchViewModel)

    single<PlacesClient> {
        Places.createClient(androidContext())
    }
    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    singleOf(::FetchPlaceSuggestionsUseCase)
    singleOf(::SaveLocationUseCase)

}