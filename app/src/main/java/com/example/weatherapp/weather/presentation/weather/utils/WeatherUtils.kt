package com.example.weatherapp.weather.presentation.weather.utils

import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.UiText

enum class WeatherDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST,
}

fun getWindDirectionText(degrees: Int): WeatherDirection {
    return when ((degrees + 22) / 45) {
        0 -> WeatherDirection.NORTH
        1 -> WeatherDirection.NORTH_EAST
        2 -> WeatherDirection.EAST
        3 -> WeatherDirection.SOUTH_EAST
        4 -> WeatherDirection.SOUTH
        5 -> WeatherDirection.SOUTH_WEST
        6 -> WeatherDirection.WEST
        7 -> WeatherDirection.NORTH_WEST
        else -> WeatherDirection.NORTH
    }
}

fun WeatherDirection.toUiText(): UiText {
    return when (this) {
        WeatherDirection.NORTH -> UiText.StringResource(R.string.north)
        WeatherDirection.NORTH_EAST -> UiText.StringResource(R.string.north_east)
        WeatherDirection.EAST -> UiText.StringResource(R.string.east)
        WeatherDirection.SOUTH_EAST -> UiText.StringResource(R.string.south_east)
        WeatherDirection.SOUTH -> UiText.StringResource(R.string.south)
        WeatherDirection.SOUTH_WEST -> UiText.StringResource(R.string.south_west)
        WeatherDirection.WEST -> UiText.StringResource(R.string.west)
        WeatherDirection.NORTH_WEST -> UiText.StringResource(R.string.north_west)
    }
}