package com.example.weatherapp.core.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDescriptionDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
