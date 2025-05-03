package com.example.weatherapp.location_list.domain.models

import com.example.weatherapp.core.domain.Error

enum class LookupAddressError: Error {
    NetworkError,
    Unknown,
}