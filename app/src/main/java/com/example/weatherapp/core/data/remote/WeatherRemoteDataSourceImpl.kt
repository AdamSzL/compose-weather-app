package com.example.weatherapp.core.data.remote

import com.example.weatherapp.core.data.remote.api.WeatherApi
import com.example.weatherapp.core.data.remote.model.ExcludePart
import com.example.weatherapp.core.data.remote.model.WeatherResponseDto
import com.example.weatherapp.core.domain.model.GeoPoint

class WeatherRemoteDataSourceImpl(
    private val weatherApi: WeatherApi
): WeatherRemoteDataSource {

    override suspend fun fetchWeather(
        location: GeoPoint,
        exclude: List<ExcludePart>
    ): WeatherResponseDto {
        return weatherApi.getWeatherResponse(location, exclude)
    }
}