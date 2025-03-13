package com.example.weatherapp.weather.domain.use_cases

import com.example.weatherapp.weather.domain.models.WeatherTileData
import com.example.weatherapp.weather.presentation.weather.fake.fakeWeatherTileData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ResetLayoutUseCaseTest {

    private lateinit var resetLayoutUseCase: ResetLayoutUseCase

    private lateinit var weatherTileDataHistory: List<List<WeatherTileData>>

    @Before
    fun setUp() {
        resetLayoutUseCase = ResetLayoutUseCase()
        val weatherTileDataBefore = fakeWeatherTileData
        val weatherTileDataAfterFirstChange = fakeWeatherTileData.toMutableList().shuffled()
        val weatherTileDataAfterSecondChange = fakeWeatherTileData.toMutableList().shuffled()
        weatherTileDataHistory = listOf(weatherTileDataBefore, weatherTileDataAfterFirstChange, weatherTileDataAfterSecondChange)
    }

    @Test
    fun resetLayoutUseCase_ResetLayout_ResetsLayout() {
        val (history, tileData, tileDataIndex) = resetLayoutUseCase(weatherTileDataHistory, 1)
        assertEquals(3, history.size)
        assertEquals(weatherTileDataHistory.first(), history.last())
        assertEquals(tileData, history.last())
        assertEquals(2, tileDataIndex)
    }

    @Test
    fun resetLayoutUseCase_ResetLayoutWhenCurrentLayoutIsLatest_CorrectlyUpdatesHistory() {
        val (history, tileData, tileDataIndex) = resetLayoutUseCase(weatherTileDataHistory, 2)
        assertEquals(4, history.size)
        assertEquals(weatherTileDataHistory.first(), history.last())
        assertEquals(tileData, history.last())
        assertEquals(3, tileDataIndex)
    }
}