package com.example.weatherapp.location_search.di

import com.example.weatherapp.location_search.data.repository.place_search.FakeLocationSearchRepository
import com.example.weatherapp.location_search.data.repository.place_search.LocationSearchRepository
import com.example.weatherapp.location_search.data.repository.place_search.LocationSearchRepositoryImpl
import com.example.weatherapp.location_search.data.repository.user_location.FakeUserLocationRepository
import com.example.weatherapp.location_search.data.repository.user_location.UserLocationRepository
import com.example.weatherapp.location_search.data.repository.user_location.UserLocationRepositoryImpl
import com.example.weatherapp.location_search.domain.use_cases.FetchPlaceSuggestionsUseCase
import com.example.weatherapp.location_search.presentation.place_search.LocationSearchViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val locationSearchModule = module {
    single<UserLocationRepository> {
//        FakeUserLocationRepository()
        UserLocationRepositoryImpl()
    }
    single<LocationSearchRepository> {
//        FakeLocationSearchRepository()
        LocationSearchRepositoryImpl(get())
    }
    viewModelOf(::LocationSearchViewModel)
    single<PlacesClient> {
        Places.createClient(androidContext())
    }
    singleOf(::FetchPlaceSuggestionsUseCase)
}