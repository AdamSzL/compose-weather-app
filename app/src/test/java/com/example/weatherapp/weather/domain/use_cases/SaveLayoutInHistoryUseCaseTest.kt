package com.example.weatherapp.weather.domain.use_cases

import com.example.weatherapp.weather.presentation.fake.fakeWeatherTileData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SaveLayoutInHistoryUseCaseTest {

    private lateinit var saveLayoutInHistoryUseCase: SaveLayoutInHistoryUseCase

    @Before
    fun setUp() {
        saveLayoutInHistoryUseCase = SaveLayoutInHistoryUseCase()
    }

    @Test
    fun saveLayoutInHistoryUseCase_SaveLayout_AddsLayoutToHistory() {
        val (newHistory, tileDataIndex) = saveLayoutInHistoryUseCase(emptyList(), fakeWeatherTileData, -1)
        assertEquals(1, newHistory.size)
        assertEquals(fakeWeatherTileData, newHistory.last())
        assertEquals(0, tileDataIndex)
    }
}