package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.core.di.dataModule
import com.example.weatherapp.core.di.networkModule
import com.example.weatherapp.location_list.di.locationListModule
import com.example.weatherapp.location_search.di.locationSearchModule
import com.example.weatherapp.weather.di.weatherModule
import com.google.android.libraries.places.api.Places
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class WeatherApp: Application() {

    override fun onCreate() {
        super.onCreate()
        val mapsApiKey = BuildConfig.MAPS_API_KEY
        if (!Places.isInitialized()) {
            Places.initializeWithNewPlacesApiEnabled(applicationContext, mapsApiKey)
        }
        startKoin {
            androidLogger()
            androidContext(this@WeatherApp)
            modules(networkModule, dataModule, weatherModule, locationListModule, locationSearchModule)
        }
    }
}