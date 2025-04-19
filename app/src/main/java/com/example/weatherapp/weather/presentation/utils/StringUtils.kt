package com.example.weatherapp.weather.presentation.utils

fun String.capitalizeWords(): String {
    return split(" ")
        .joinToString(" ") {
            it.replaceFirstChar { char -> char.uppercaseChar() }
        }
}