package com.example.weatherapp.location_list.domain.models

import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.GeoLocation
import java.util.UUID

data class LocationWeatherBrief(
    val id: String = UUID.randomUUID().toString(),
    val location: GeoLocation,
    val weatherBrief: BriefWeather? = null,
)