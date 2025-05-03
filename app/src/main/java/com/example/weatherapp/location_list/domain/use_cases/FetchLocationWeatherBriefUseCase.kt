package com.example.weatherapp.location_list.domain.use_cases

import com.example.weatherapp.core.data.repository.WeatherRepository
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.location_list.data.repository.saved_locations.SavedLocationsRepository
import com.example.weatherapp.location_list.domain.models.LocationWeatherBrief
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchLocationWeatherBriefUseCase(
    private val savedLocationsRepository: SavedLocationsRepository,
    private val weatherRepository: WeatherRepository,
) {

    suspend operator fun invoke(): Flow<List<LocationWeatherBrief>> {
        return savedLocationsRepository.getAllLocations()
            .map { locations ->
                locations.map { location ->
                    val weatherBrief = weatherRepository.getBriefWeather(location)
                    LocationWeatherBrief(
                        location = location,
                        weatherBrief = if (weatherBrief is Result.Success) {
                            weatherBrief.data
                        } else null,
                    )
                }
            }
    }
}