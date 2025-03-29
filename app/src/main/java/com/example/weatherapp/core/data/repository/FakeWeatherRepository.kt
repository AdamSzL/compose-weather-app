package com.example.weatherapp.core.data.repository

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.error.GetWeatherError
import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DailyForecast
import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.domain.model.HourlyForecast
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeLocations
import com.example.weatherapp.weather.presentation.weather.fake.fakeBriefWeather
import com.example.weatherapp.weather.presentation.weather.fake.fakeDetailedWeather

class FakeWeatherRepository(
    private val shouldReturnError: Boolean = false
): WeatherRepository {

    override suspend fun getBriefWeather(location: GeoPoint): Result<BriefWeather, GetWeatherError> {
        return if (shouldReturnError) {
            Result.Error(GetWeatherError.NetworkError)
        } else {
            Result.Success(fakeLocations.find { it.location.coordinates == location }!!.weatherBrief!!)
        }
    }

    override suspend fun getDetailedWeather(location: GeoPoint): Result<DetailedWeather, GetWeatherError> {
        return if (shouldReturnError) {
            Result.Error(GetWeatherError.NetworkError)
        } else {
            Result.Success(fakeDetailedWeather)
        }
    }

    override suspend fun getDailyForecast(location: GeoPoint): Result<List<DailyForecast>, GetWeatherError> {
        return if (shouldReturnError) {
            Result.Error(GetWeatherError.NetworkError)
        } else {
            Result.Success(emptyList())
        }
    }

    override suspend fun getHourlyForecast(location: GeoPoint): Result<List<HourlyForecast>, GetWeatherError> {
        return if (shouldReturnError) {
            Result.Error(GetWeatherError.NetworkError)
        } else {
            Result.Success(emptyList())
        }
    }
}