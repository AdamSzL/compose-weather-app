package com.example.weatherapp.locations.domain.use_cases

import com.example.weatherapp.locations.domain.models.GeoLocation

class DeleteLocationUseCase {
    operator fun invoke(savedLocations: List<GeoLocation>, locationId: String): List<GeoLocation> {
        return savedLocations.filter { it.id != locationId }
    }
}