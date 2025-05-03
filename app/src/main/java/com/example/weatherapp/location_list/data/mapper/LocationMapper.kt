package com.example.weatherapp.location_list.data.mapper

import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_list.data.local.entity.SavedLocationEntity

fun SavedLocationEntity.toGeoLocation(): GeoLocation {
    return GeoLocation(
        id = id,
        coordinates = GeoPoint(
            longitude = longitude,
            latitude = latitude
        ),
        address = GeoAddress(
            name = name,
            country = country
        )
    )
}

fun GeoLocation.toSavedLocationEntity(): SavedLocationEntity {
    return SavedLocationEntity(
        id = id,
        latitude = coordinates.latitude,
        longitude = coordinates.longitude,
        name = address.name,
        country = address.country
    )
}