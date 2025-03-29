package com.example.weatherapp.locations.domain.use_cases

import com.example.weatherapp.locations.domain.models.LocationWeatherBrief

class DeleteLocationUseCase {
    operator fun invoke(locations: List<LocationWeatherBrief>, locationId: String): List<LocationWeatherBrief> {
        return locations.filter { it.id != locationId }
    }
}