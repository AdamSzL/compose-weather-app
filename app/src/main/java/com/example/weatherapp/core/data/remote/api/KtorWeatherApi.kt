package com.example.weatherapp.core.data.remote.api

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.core.data.remote.model.ExcludePart
import com.example.weatherapp.core.data.remote.model.WeatherResponseDto
import com.example.weatherapp.core.domain.model.GeoPoint
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class KtorWeatherApi(
    private val client: HttpClient
): WeatherApi {

    override suspend fun getWeatherResponse(
        location: GeoPoint,
        exclude: List<ExcludePart>
    ): WeatherResponseDto {
        return client.get(API_BASE_URL) {
            parameter("lat", location.latitude)
            parameter("lon", location.longitude)
            parameter("exclude", exclude.joinToString(",") { it.value })
            parameter("units", "metric")
            parameter("appid", BuildConfig.WEATHER_API_KEY)
        }.body()
    }

    companion object {
        private const val API_BASE_URL = "https://api.openweathermap.org/data/3.0/onecall"
    }
}