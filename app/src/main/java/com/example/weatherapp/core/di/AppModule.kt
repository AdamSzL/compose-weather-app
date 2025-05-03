package com.example.weatherapp.core.di

import androidx.room.Room
import com.example.weatherapp.core.data.local.WeatherDatabase
import com.example.weatherapp.core.data.local.WeatherLocalDataSource
import com.example.weatherapp.core.data.local.WeatherLocalDataSourceImpl
import com.example.weatherapp.core.data.local.dao.SavedWeatherDao
import com.example.weatherapp.core.data.remote.WeatherRemoteDataSource
import com.example.weatherapp.core.data.remote.WeatherRemoteDataSourceImpl
import com.example.weatherapp.core.data.repository.WeatherRepository
import com.example.weatherapp.core.data.repository.WeatherRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
                androidContext(),
                WeatherDatabase::class.java,
                "weather_database"
            ).fallbackToDestructiveMigration(false)
            .build()
    }

    single<WeatherRemoteDataSource> {
        WeatherRemoteDataSourceImpl(get())
    }

    single<WeatherLocalDataSource> {
        WeatherLocalDataSourceImpl(get())
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(get(), get())
    }

    single<SavedWeatherDao> {
        get<WeatherDatabase>().savedWeatherDao()
    }
}