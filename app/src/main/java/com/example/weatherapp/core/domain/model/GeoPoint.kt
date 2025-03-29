package com.example.weatherapp.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GeoPoint(
    val longitude: Double,
    val latitude: Double
)