package com.example.weatherapp.core.data.remote.api

import com.example.weatherapp.core.data.remote.model.ExcludePart
import com.example.weatherapp.core.data.remote.model.WeatherResponseDto
import com.example.weatherapp.core.domain.model.GeoPoint

interface WeatherApi {

    suspend fun getWeatherResponse(
        location: GeoPoint,
        exclude: List<ExcludePart> = emptyList()
    ): WeatherResponseDto

}