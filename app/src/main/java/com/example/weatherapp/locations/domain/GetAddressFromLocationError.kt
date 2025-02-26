package com.example.weatherapp.locations.domain

import com.example.weatherapp.core.domain.Error

enum class GetAddressFromLocationError: Error {
    LocationFetchFailed,
    AddressFetchFailed
}