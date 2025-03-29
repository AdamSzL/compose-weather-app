package com.example.weatherapp.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RainSnowDto(
    @SerialName("1h") val oneHour: Double? = null
)
