package com.example.weatherapp.locations.presentation.saved_locations.mock

import com.example.weatherapp.locations.domain.SavedLocation
import java.util.UUID

val mockSavedLocations = listOf(
    SavedLocation(
        id = UUID.randomUUID().toString(),
        name = "New York City",
        latitude = 40.7128,
        longitude = -74.0060,
        country = "USA",
    ),
    SavedLocation(
        id = UUID.randomUUID().toString(),
        name = "Eiffel Tower",
        latitude = 48.8584,
        longitude = 2.2945,
        country = "France",
    ),
    SavedLocation(
        id = UUID.randomUUID().toString(),
        name = "Tokyo",
        latitude = 35.682839,
        longitude = 139.759455,
        country = "Japan",
    ),
    SavedLocation(
        id = UUID.randomUUID().toString(),
        name = "Unknown Location",
        latitude = -33.865143,
        longitude = 151.209900,
        country = null,
    ),
    SavedLocation(
        id = UUID.randomUUID().toString(),
        name = "Rio de Janeiro",
        latitude = -22.9068,
        longitude = -43.1729,
        country = "Brazil",
    ),
    SavedLocation(
        id = UUID.randomUUID().toString(),
        name = "Deserted Island",
        latitude = 12.3456,
        longitude = -98.7654,
        country = null,
    ),
    SavedLocation(
        id = UUID.randomUUID().toString(),
        name = "Cape Town",
        latitude = -33.9249,
        longitude = 18.4241,
        country = "South Africa",
    ),
    SavedLocation(
        id = UUID.randomUUID().toString(),
        name = "Machu Picchu",
        latitude = -13.1631,
        longitude = -72.5450,
        country = "Peru",
    ),
    SavedLocation(
        id = UUID.randomUUID().toString(),
        name = "Sydney Opera House",
        latitude = -33.8568,
        longitude = 151.2153,
        country = "Australia",
    ),
    SavedLocation(
        id = UUID.randomUUID().toString(),
        name = "Antarctica Research Base",
        latitude = -75.2509,
        longitude = -0.0714,
        country = null,
    )
)