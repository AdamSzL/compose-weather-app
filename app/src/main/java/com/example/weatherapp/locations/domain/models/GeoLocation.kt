package com.example.weatherapp.locations.domain.models

import java.util.UUID

data class GeoLocation(
    val id: String = UUID.randomUUID().toString(),
    val coordinates: GeoPoint,
    val address: GeoAddress
)
