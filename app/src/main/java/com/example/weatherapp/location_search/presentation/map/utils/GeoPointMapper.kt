package com.example.weatherapp.location_search.presentation.map.utils

import com.example.weatherapp.core.domain.model.GeoPoint
import com.google.android.gms.maps.model.LatLng

fun LatLng.toGeoPoint(): GeoPoint {
    return GeoPoint(
        latitude = latitude,
        longitude = longitude
    )
}

fun GeoPoint.toLatLng(): LatLng {
    return LatLng(
        latitude,
        longitude
    )
}