package com.example.weatherapp.core.data.repository

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.error.GetWeatherError
import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.core.domain.model.GeoLocation

interface WeatherRepository {

    suspend fun getBriefWeather(location: GeoLocation): Result<BriefWeather, GetWeatherError>

    suspend fun getDetailedWeather(location: GeoLocation): Result<DetailedWeather, GetWeatherError>

    suspend fun refreshCurrentWeather(location: GeoLocation): Result<Unit, GetWeatherError>

    suspend fun refreshCurrentWeatherIfRefreshable(location: GeoLocation): Result<Unit, GetWeatherError>

//    suspend fun getDailyForecast(location: GeoPoint): Result<List<DailyForecast>, GetWeatherError>
//
//    suspend fun getHourlyForecast(location: GeoPoint): Result<List<HourlyForecast>, GetWeatherError>

}