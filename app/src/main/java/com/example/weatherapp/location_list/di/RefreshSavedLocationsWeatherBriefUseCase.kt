package com.example.weatherapp.location_list.di

import com.example.weatherapp.core.data.repository.WeatherRepository
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.error.GetWeatherError
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.location_list.domain.models.RefreshCurrentWeatherResult

class RefreshSavedLocationsWeatherBriefUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(savedLocations: List<GeoLocation>): RefreshCurrentWeatherResult {
        val refreshResults = mutableListOf<Result<Boolean, GetWeatherError>>()
        savedLocations.forEach { location ->
            val refreshResult = weatherRepository.refreshCurrentWeatherIfRefreshable(location)
            refreshResults.add(refreshResult)
        }

        val anyError = refreshResults.any { it is Result.Error }
        val anyRefreshed = refreshResults.any { it is Result.Success && it.data }
        return when {
            anyError -> RefreshCurrentWeatherResult.Error
            anyRefreshed -> RefreshCurrentWeatherResult.Updated
            else -> RefreshCurrentWeatherResult.UpToDate
        }
    }
}