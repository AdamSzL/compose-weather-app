package com.example.weatherapp.location_list.domain.use_cases

import com.example.weatherapp.location_list.domain.models.LocationWeatherBrief

class DeleteLocationUseCase {
    operator fun invoke(locations: List<LocationWeatherBrief>, locationId: String): List<LocationWeatherBrief> {
        return locations.filter { it.id != locationId }
    }
}