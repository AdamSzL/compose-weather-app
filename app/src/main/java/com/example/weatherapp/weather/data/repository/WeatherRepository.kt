package com.example.weatherapp.weather.data.repository

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.weather.domain.models.GetCurrentWeatherError
import com.example.weatherapp.weather.domain.models.WeatherInfo

interface WeatherRepository {

    suspend fun getCurrentWeather(): Result<WeatherInfo, GetCurrentWeatherError>

}