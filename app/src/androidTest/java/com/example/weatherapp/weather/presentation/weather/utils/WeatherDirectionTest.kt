package com.example.weatherapp.weather.presentation.weather.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.weather.presentation.utils.WeatherDirection
import com.example.weatherapp.weather.presentation.utils.toUiText
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherDirectionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun toUiText_North_ReturnsCorrectResource() {
        val weatherDirection = WeatherDirection.NORTH
        val uiText = weatherDirection.toUiText()
        assertEquals(UiText.StringResource(R.string.north).asString(context), uiText.asString(context))
    }

    @Test
    fun toUiText_South_ReturnsCorrectResource() {
        val weatherDirection = WeatherDirection.SOUTH
        val uiText = weatherDirection.toUiText()
        assertEquals(UiText.StringResource(R.string.south).asString(context), uiText.asString(context))
    }

    @Test
    fun toUiText_West_ReturnsCorrectResource() {
        val weatherDirection = WeatherDirection.WEST
        val uiText = weatherDirection.toUiText()
        assertEquals(UiText.StringResource(R.string.west).asString(context), uiText.asString(context))
    }


}