package com.example.weatherapp.location_list.di

import android.location.Geocoder
import com.example.weatherapp.core.data.local.WeatherDatabase
import com.example.weatherapp.location_list.data.local.dao.SavedLocationsDao
import com.example.weatherapp.location_list.data.repository.location_address.LocationAddressRepository
import com.example.weatherapp.location_list.data.repository.location_address.LocationAddressRepositoryImpl
import com.example.weatherapp.location_list.data.repository.saved_locations.SavedLocationsRepository
import com.example.weatherapp.location_list.data.repository.saved_locations.SavedLocationsRepositoryImpl
import com.example.weatherapp.location_list.domain.use_cases.FetchLocationWeatherBriefUseCase
import com.example.weatherapp.location_list.domain.use_cases.RefreshSavedLocationsWeatherBriefUseCase
import com.example.weatherapp.location_list.presentation.LocationListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import java.util.Locale

val locationListModule = module {
    single {
        Geocoder(get(), Locale.getDefault())
    }
    single<SavedLocationsRepository> {
        SavedLocationsRepositoryImpl(get())
    }
    single<LocationAddressRepository> {
        LocationAddressRepositoryImpl(get())
    }
    single<SavedLocationsDao> {
        get<WeatherDatabase>().savedLocationsDao()
    }
    viewModelOf(::LocationListViewModel)
    singleOf(::FetchLocationWeatherBriefUseCase)
    singleOf(::RefreshSavedLocationsWeatherBriefUseCase)
}
