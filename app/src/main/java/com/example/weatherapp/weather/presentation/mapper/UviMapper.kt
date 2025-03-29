package com.example.weatherapp.weather.presentation.mapper

import com.example.weatherapp.R

fun uviToRiskLevel(uvi: Double): Int {
    return when (uvi) {
        in 0.0..2.99 -> R.string.uvi_risk_low
        in 3.0..5.99 -> R.string.uvi_risk_moderate
        in 6.0..7.99 -> R.string.uvi_risk_high
        in 8.0..10.99 -> R.string.uvi_risk_very_high
        else -> R.string.uvi_risk_extreme
    }
}