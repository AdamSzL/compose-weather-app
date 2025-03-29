package com.example.weatherapp.core.data.repository

import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.error.GetWeatherError
import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DailyForecast
import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.domain.model.HourlyForecast

interface WeatherRepository {

    suspend fun getBriefWeather(location: GeoPoint): Result<BriefWeather, GetWeatherError>

    suspend fun getDetailedWeather(location: GeoPoint): Result<DetailedWeather, GetWeatherError>

    suspend fun getDailyForecast(location: GeoPoint): Result<List<DailyForecast>, GetWeatherError>

    suspend fun getHourlyForecast(location: GeoPoint): Result<List<HourlyForecast>, GetWeatherError>

}