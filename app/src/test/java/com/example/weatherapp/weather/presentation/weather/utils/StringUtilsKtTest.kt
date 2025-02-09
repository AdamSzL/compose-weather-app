package com.example.weatherapp.weather.presentation.weather.utils

import org.junit.Assert.*

import org.junit.Test

class StringUtilsKtTest {

    @Test
    fun capitalizeWords_twoWordsString_returnsCapitalizedString() {
        val input = "moderate rain"
        val expected = "Moderate Rain"
        val actual = input.capitalizeWords()
        assertEquals(expected, actual)
    }

    @Test
    fun capitalizeWords_oneWordString_returnsCapitalizedString() {
        val input = "rain"
        val expected = "Rain"
        val actual = input.capitalizeWords()
        assertEquals(expected, actual)
    }

}