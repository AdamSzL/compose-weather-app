package com.example.weatherapp.locations.presentation.saved_locations.fake

import com.example.weatherapp.locations.domain.models.GeoAddress
import com.example.weatherapp.locations.domain.models.GeoLocation
import com.example.weatherapp.locations.domain.models.GeoPoint
import java.util.UUID

val fakeSavedLocations = listOf(
    GeoLocation(
        id = UUID.randomUUID().toString(),
        coordinates = GeoPoint(latitude = 40.7128, longitude = -74.0060),
        address = GeoAddress("New York City", "USA")
    ),
    GeoLocation(
        id = UUID.randomUUID().toString(),
        coordinates = GeoPoint(latitude = 48.8584, longitude = 2.2945),
        address = GeoAddress("Eiffel Tower", "France")
    ),
    GeoLocation(
        id = UUID.randomUUID().toString(),
        coordinates = GeoPoint(latitude = 35.682839, longitude = 139.759455),
        address = GeoAddress("Tokyo", "Japan")
    ),
    GeoLocation(
        id = UUID.randomUUID().toString(),
        coordinates = GeoPoint(latitude = -33.865143, longitude = 151.209900),
        address = GeoAddress("Unknown Location", null)
    ),
    GeoLocation(
        id = UUID.randomUUID().toString(),
        coordinates = GeoPoint(latitude = -22.9068, longitude = -43.1729),
        address = GeoAddress("Rio de Janeiro", "Brazil")
    ),
    GeoLocation(
        id = UUID.randomUUID().toString(),
        coordinates = GeoPoint(latitude = 12.3456, longitude = -98.7654),
        address = GeoAddress("Deserted Island", null)
    ),
    GeoLocation(
        id = UUID.randomUUID().toString(),
        coordinates = GeoPoint(latitude = -33.9249, longitude = 18.4241),
        address = GeoAddress("Cape Town", "South Africa")
    ),
    GeoLocation(
        id = UUID.randomUUID().toString(),
        coordinates = GeoPoint(latitude = -33.8568, longitude = 151.2153),
        address = GeoAddress("Sydney Opera House", "Australia")
    ),
    GeoLocation(
        id = UUID.randomUUID().toString(),
        coordinates = GeoPoint(latitude = -75.2509, longitude = -0.0714),
        address = GeoAddress("Antarctica Research Base", null)
    )
)

val fakeUserLocation = GeoLocation(
    id = UUID.randomUUID().toString(),
    coordinates = GeoPoint(latitude = -13.1631, longitude = -72.5450),
    address = GeoAddress("Machu Picchu", "Peru")
)