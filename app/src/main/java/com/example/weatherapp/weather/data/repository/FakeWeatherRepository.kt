package com.example.weatherapp.weather.data.repository

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.weather.domain.models.GetCurrentWeatherError
import com.example.weatherapp.weather.domain.models.WeatherInfo
import com.example.weatherapp.weather.presentation.weather.fake.fakeWeatherInfo

class FakeWeatherRepository(
    private val shouldReturnError: Boolean = false
): WeatherRepository {

    override suspend fun getCurrentWeather(): Result<WeatherInfo, GetCurrentWeatherError> {
        return if (shouldReturnError) {
            Result.Error(GetCurrentWeatherError.NetworkError)
        } else {
            Result.Success(fakeWeatherInfo)
        }
    }
}