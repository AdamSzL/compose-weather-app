package com.example.weatherapp.core.di

import com.example.weatherapp.core.data.local.WeatherLocalDataSource
import com.example.weatherapp.core.data.local.WeatherLocalDataSourceImpl
import com.example.weatherapp.core.data.remote.WeatherRemoteDataSource
import com.example.weatherapp.core.data.remote.WeatherRemoteDataSourceImpl
import com.example.weatherapp.core.data.repository.WeatherRepository
import com.example.weatherapp.core.data.repository.WeatherRepositoryImpl
import org.koin.dsl.module

val dataModule = module {

    single<WeatherRemoteDataSource> {
        WeatherRemoteDataSourceImpl(get())
    }

    single<WeatherLocalDataSource> {
        WeatherLocalDataSourceImpl()
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(get(), get())
    }
}