package com.example.weatherapp.location_list.domain.models

import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.GeoLocation

data class LocationWeatherBrief(
    val location: GeoLocation,
    val weatherBrief: BriefWeather? = null,
)