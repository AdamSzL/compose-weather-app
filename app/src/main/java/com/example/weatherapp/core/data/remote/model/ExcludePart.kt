package com.example.weatherapp.core.data.remote.model

enum class ExcludePart(val value: String) {
    CURRENT("current"),
    MINUTELY("minutely"),
    HOURLY("hourly"),
    DAILY("daily"),
    ALERTS("alerts")
}