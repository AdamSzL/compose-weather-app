package com.example.weatherapp.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GeoLocation(
    val id: Long,
    val coordinates: GeoPoint,
    val address: GeoAddress
)
