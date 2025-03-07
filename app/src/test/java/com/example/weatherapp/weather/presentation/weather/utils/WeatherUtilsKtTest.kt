package com.example.weatherapp.weather.presentation.weather.utils

import org.junit.Assert.*
import org.junit.Test

class WeatherUtilsKtTest {

    @Test
    fun getWindDirectionText_degreesZero_returnsNorth() {
        val degrees = 0
        val expected = WeatherDirection.NORTH
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees45_returnsNorthEast() {
        val degrees = 45
        val expected = WeatherDirection.NORTH_EAST
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees22_returnsNorth() {
        val degrees = 22
        val expected = WeatherDirection.NORTH
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees23_returnsNorthEast() {
        val degrees = 23
        val expected = WeatherDirection.NORTH_EAST
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees67_returnsNorthEast() {
        val degrees = 67
        val expected = WeatherDirection.NORTH_EAST
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees90_returnsEast() {
        val degrees = 90
        val expected = WeatherDirection.EAST
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees112_returnsEast() {
        val degrees = 112
        val expected = WeatherDirection.EAST
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees113_returnsSouthEast() {
        val degrees = 113
        val expected = WeatherDirection.SOUTH_EAST
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees135_returnsSouthEast() {
        val degrees = 135
        val expected = WeatherDirection.SOUTH_EAST
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees180_returnsSouth() {
        val degrees = 180
        val expected = WeatherDirection.SOUTH
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees225_returnsSouthWest() {
        val degrees = 225
        val expected = WeatherDirection.SOUTH_WEST
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees270_returnsWest() {
        val degrees = 270
        val expected = WeatherDirection.WEST
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }

    @Test
    fun getWindDirectionText_degrees315_returnsNorthWest() {
        val degrees = 315
        val expected = WeatherDirection.NORTH_WEST
        val actual = getWindDirectionText(degrees)
        assertEquals(expected, actual)
    }
}