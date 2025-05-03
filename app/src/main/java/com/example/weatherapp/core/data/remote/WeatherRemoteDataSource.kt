package com.example.weatherapp.core.data.remote

import com.example.weatherapp.core.data.remote.model.ExcludePart
import com.example.weatherapp.core.data.remote.model.WeatherResponseDto
import com.example.weatherapp.core.domain.model.GeoPoint

interface WeatherRemoteDataSource {

    suspend fun fetchWeather(location: GeoPoint, exclude: List<ExcludePart>): WeatherResponseDto
}