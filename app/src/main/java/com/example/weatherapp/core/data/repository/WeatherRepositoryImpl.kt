package com.example.weatherapp.core.data.repository

import com.example.weatherapp.core.data.local.WeatherLocalDataSource
import com.example.weatherapp.core.data.remote.WeatherRemoteDataSource
import com.example.weatherapp.core.data.remote.model.ExcludePart
import com.example.weatherapp.core.data.remote.model.toBriefWeather
import com.example.weatherapp.core.data.remote.model.toDailyForecastList
import com.example.weatherapp.core.data.remote.model.toDetailedWeather
import com.example.weatherapp.core.data.remote.model.toHourlyForecastList
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.error.GetWeatherError
import com.example.weatherapp.core.domain.error.toGetWeatherError
import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DailyForecast
import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.domain.model.HourlyForecast

class WeatherRepositoryImpl(
    private val weatherLocalDataSource: WeatherLocalDataSource,
    private val weatherRemoteDataSource: WeatherRemoteDataSource
): WeatherRepository {

    override suspend fun getBriefWeather(location: GeoPoint): Result<BriefWeather, GetWeatherError> {
        val exclude = listOf(ExcludePart.MINUTELY, ExcludePart.HOURLY, ExcludePart.DAILY, ExcludePart.ALERTS)
        return try {
            Result.Success(weatherRemoteDataSource.getWeather(location, exclude).current.toBriefWeather())
        } catch (e: Exception) {
            Result.Error(e.toGetWeatherError())
        }
    }

    override suspend fun getDetailedWeather(location: GeoPoint): Result<DetailedWeather, GetWeatherError> {
        val exclude = listOf(ExcludePart.MINUTELY, ExcludePart.HOURLY, ExcludePart.DAILY, ExcludePart.ALERTS)
        return try {
            Result.Success(weatherRemoteDataSource.getWeather(location, exclude).current.toDetailedWeather())
        } catch (e: Exception) {
            Result.Error(e.toGetWeatherError())
        }
    }

    override suspend fun getDailyForecast(location: GeoPoint): Result<List<DailyForecast>, GetWeatherError> {
        val exclude = listOf(ExcludePart.MINUTELY, ExcludePart.HOURLY, ExcludePart.CURRENT, ExcludePart.ALERTS)
        return try {
            Result.Success(weatherRemoteDataSource.getWeather(location, exclude).daily?.toDailyForecastList() ?: emptyList())
        } catch (e: Exception) {
            Result.Error(e.toGetWeatherError())
        }
    }

    override suspend fun getHourlyForecast(location: GeoPoint): Result<List<HourlyForecast>, GetWeatherError> {
        val exclude = listOf(ExcludePart.MINUTELY, ExcludePart.DAILY, ExcludePart.CURRENT, ExcludePart.ALERTS)
        return try {
            Result.Success(weatherRemoteDataSource.getWeather(location, exclude).hourly?.toHourlyForecastList() ?: emptyList())
        } catch (e: Exception) {
            Result.Error(e.toGetWeatherError())
        }
    }
}