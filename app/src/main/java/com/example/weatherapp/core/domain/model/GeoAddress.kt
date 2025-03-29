package com.example.weatherapp.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GeoAddress(
    val name: String,
    val country: String?
)

fun GeoAddress.formattedAddress(): String {
    return if (!country.isNullOrBlank()) "$name, $country" else name
}