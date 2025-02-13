package com.example.weatherapp.locations.domain

import java.util.UUID

data class SavedLocation(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?,
    val source: LocationSource,
)
