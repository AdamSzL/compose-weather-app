package com.example.weatherapp.core.data.repository

import com.example.weatherapp.core.data.local.WeatherLocalDataSource
import com.example.weatherapp.core.data.local.mapper.toBriefWeather
import com.example.weatherapp.core.data.local.mapper.toDetailedWeather
import com.example.weatherapp.core.data.local.utils.isExpired
import com.example.weatherapp.core.data.local.utils.isRefreshable
import com.example.weatherapp.core.data.mapper.toEntity
import com.example.weatherapp.core.data.remote.WeatherRemoteDataSource
import com.example.weatherapp.core.data.remote.model.ExcludePart
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.error.GetWeatherError
import com.example.weatherapp.core.domain.error.toGetWeatherError
import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.core.domain.model.GeoLocation

class WeatherRepositoryImpl(
    private val weatherLocalDataSource: WeatherLocalDataSource,
    private val weatherRemoteDataSource: WeatherRemoteDataSource
): WeatherRepository {

    override suspend fun getBriefWeather(location: GeoLocation): Result<BriefWeather, GetWeatherError> {
        return try {
            var savedWeather = weatherLocalDataSource.getSavedWeather(location.id)
            if (savedWeather == null || savedWeather.isExpired()) {
                refreshCurrentWeather(location)
                savedWeather = weatherLocalDataSource.getSavedWeather(location.id)
            }
            savedWeather?.let {
                Result.Success(it.toBriefWeather())
            } ?: Result.Error(GetWeatherError.UnknownError)
        } catch (e: Exception) {
            Result.Error(e.toGetWeatherError())
        }
    }

    override suspend fun getDetailedWeather(location: GeoLocation): Result<DetailedWeather, GetWeatherError> {
        return try {
            var savedWeather = weatherLocalDataSource.getSavedWeather(location.id)
            if (savedWeather == null || savedWeather.isExpired()) {
                refreshCurrentWeather(location)
                savedWeather = weatherLocalDataSource.getSavedWeather(location.id)
            }
            savedWeather?.let {
                Result.Success(it.toDetailedWeather())
            } ?: Result.Error(GetWeatherError.UnknownError)
        } catch (e: Exception) {
            Result.Error(e.toGetWeatherError())
        }
    }

    override suspend fun refreshCurrentWeather(location: GeoLocation): Result<Unit, GetWeatherError> {
        val exclude = listOf(ExcludePart.MINUTELY, ExcludePart.HOURLY, ExcludePart.DAILY, ExcludePart.ALERTS)
        return try {
            val weatherResponse = weatherRemoteDataSource.fetchWeather(location.coordinates, exclude).current
            weatherLocalDataSource.saveCurrentWeather(weatherResponse.toEntity(location.id))
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.toGetWeatherError())
        }
    }

    override suspend fun refreshCurrentWeatherIfRefreshable(location: GeoLocation): Result<Unit, GetWeatherError> {
        val savedWeather = weatherLocalDataSource.getSavedWeather(location.id)

        return if (savedWeather == null || savedWeather.isRefreshable()) {
            refreshCurrentWeather(location)
        } else {
            Result.Success(Unit)
        }
    }

//    override suspend fun getDailyForecast(location: GeoPoint): Result<List<DailyForecast>, GetWeatherError> {
//        val exclude = listOf(ExcludePart.MINUTELY, ExcludePart.HOURLY, ExcludePart.CURRENT, ExcludePart.ALERTS)
//        return try {
//            Result.Success(weatherRemoteDataSource.getWeather(location, exclude).daily?.toDailyForecastList() ?: emptyList())
//        } catch (e: Exception) {
//            Result.Error(e.toGetWeatherError())
//        }
//    }
//
//    override suspend fun getHourlyForecast(location: GeoPoint): Result<List<HourlyForecast>, GetWeatherError> {
//        val exclude = listOf(ExcludePart.MINUTELY, ExcludePart.DAILY, ExcludePart.CURRENT, ExcludePart.ALERTS)
//        return try {
//            Result.Success(weatherRemoteDataSource.getWeather(location, exclude).hourly?.toHourlyForecastList() ?: emptyList())
//        } catch (e: Exception) {
//            Result.Error(e.toGetWeatherError())
//        }
//    }
}