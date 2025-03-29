package com.example.weatherapp.core.di

import com.example.weatherapp.core.data.remote.api.KtorWeatherApi
import com.example.weatherapp.core.data.remote.api.WeatherApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {

    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }


    single<WeatherApi> {
        KtorWeatherApi(
            client = get()
        )
    }
}