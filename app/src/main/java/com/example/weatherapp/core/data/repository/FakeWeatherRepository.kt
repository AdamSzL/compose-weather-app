package com.example.weatherapp.core.data.repository

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.error.GetWeatherError
import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.fake.fakeLocationWeatherBriefs
import com.example.weatherapp.weather.presentation.fake.fakeDetailedWeather

class FakeWeatherRepository(
    private val shouldReturnError: Boolean = false
): WeatherRepository {

    override suspend fun getBriefWeather(location: GeoLocation): Result<BriefWeather, GetWeatherError> {
        return if (shouldReturnError) {
            Result.Error(GetWeatherError.NetworkError)
        } else {
            Result.Success(fakeLocationWeatherBriefs.find { it.location.coordinates == location.coordinates }!!.weatherBrief!!)
        }
    }

    override suspend fun getDetailedWeather(location: GeoLocation): Result<DetailedWeather, GetWeatherError> {
        return if (shouldReturnError) {
            Result.Error(GetWeatherError.NetworkError)
        } else {
            Result.Success(fakeDetailedWeather)
        }
    }

    override suspend fun refreshCurrentWeather(location: GeoLocation): Result<Unit, GetWeatherError> {
        return if (shouldReturnError) {
            Result.Error(GetWeatherError.NetworkError)
        } else {
            Result.Success(Unit)
        }
    }

    override suspend fun refreshCurrentWeatherIfRefreshable(location: GeoLocation): Result<Boolean, GetWeatherError> {
        return if (shouldReturnError) {
            Result.Error(GetWeatherError.UnknownError)
        } else {
            Result.Success(true)
        }
    }

//    override suspend fun getDailyForecast(location: GeoPoint): Result<List<DailyForecast>, GetWeatherError> {
//        return if (shouldReturnError) {
//            Result.Error(GetWeatherError.NetworkError)
//        } else {
//            Result.Success(emptyList())
//        }
//    }
//
//    override suspend fun getHourlyForecast(location: GeoPoint): Result<List<HourlyForecast>, GetWeatherError> {
//        return if (shouldReturnError) {
//            Result.Error(GetWeatherError.NetworkError)
//        } else {
//            Result.Success(emptyList())
//        }
//    }
}