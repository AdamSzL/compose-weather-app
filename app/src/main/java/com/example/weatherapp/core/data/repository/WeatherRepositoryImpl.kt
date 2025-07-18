package com.example.weatherapp.core.data.repository

import com.example.weatherapp.core.data.local.WeatherLocalDataSource
import com.example.weatherapp.core.data.local.mapper.toBriefWeather
import com.example.weatherapp.core.data.local.mapper.toDetailedWeather
import com.example.weatherapp.core.data.local.utils.isExpired
import com.example.weatherapp.core.data.local.utils.isRefreshable
import com.example.weatherapp.core.data.mapper.toFullWeatherEntity
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
            var savedWeather = weatherLocalDataSource.getFullWeather(location.id)
            if (savedWeather == null || savedWeather.current.isExpired()) {
                refreshCurrentWeather(location)
                savedWeather = weatherLocalDataSource.getFullWeather(location.id)
            }
            savedWeather?.let {
                Result.Success(it.current.toBriefWeather())
            } ?: Result.Error(GetWeatherError.UnknownError)
        } catch (e: Exception) {
            Result.Error(e.toGetWeatherError())
        }
    }

    override suspend fun getDetailedWeather(location: GeoLocation): Result<DetailedWeather, GetWeatherError> {
        return try {
            var savedWeather = weatherLocalDataSource.getFullWeather(location.id)
            if (savedWeather == null || savedWeather.current.isExpired()) {
                refreshCurrentWeather(location)
                savedWeather = weatherLocalDataSource.getFullWeather(location.id)
            }
            savedWeather?.let {
                Result.Success(it.toDetailedWeather())
            } ?: Result.Error(GetWeatherError.UnknownError)
        } catch (e: Exception) {
            Result.Error(e.toGetWeatherError())
        }
    }

    override suspend fun refreshCurrentWeather(location: GeoLocation): Result<Unit, GetWeatherError> {
        val exclude = listOf(ExcludePart.MINUTELY, ExcludePart.ALERTS)
        return try {
            val weatherResponse = weatherRemoteDataSource.fetchWeather(location.coordinates, exclude)
            weatherLocalDataSource.saveFullWeather(weatherResponse.toFullWeatherEntity(location.id))
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.toGetWeatherError())
        }
    }

    override suspend fun refreshCurrentWeatherIfRefreshable(location: GeoLocation): Result<Boolean, GetWeatherError> {
        val savedWeather = weatherLocalDataSource.getFullWeather(location.id)

        return if (savedWeather == null || savedWeather.current.isRefreshable()) {
            when (val refreshResult = refreshCurrentWeather(location)) {
                is Result.Success -> Result.Success(true)
                is Result.Error -> Result.Error(refreshResult.error)
            }
        } else {
            Result.Success(false)
        }
    }
}