package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.core.di.appModule
import com.example.weatherapp.locations.di.locationsModule
import com.example.weatherapp.weather.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class WeatherApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WeatherApp)
            modules(appModule, weatherModule, locationsModule)
        }
    }
}