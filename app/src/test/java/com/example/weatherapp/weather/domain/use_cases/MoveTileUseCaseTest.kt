package com.example.weatherapp.weather.domain.use_cases

import com.example.weatherapp.weather.presentation.fake.fakeWeatherTileData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoveTileUseCaseTest {

    private lateinit var moveTileUseCase: MoveTileUseCase

    @Before
    fun setUp() {
        moveTileUseCase = MoveTileUseCase()
    }

    @Test
    fun moveTileUseCase_MoveTile_CorrectlySwapsTiles() {
        val result = moveTileUseCase(fakeWeatherTileData, 0, 1)
        assertEquals(fakeWeatherTileData[1], result[0])
        assertEquals(fakeWeatherTileData[0], result[1])
        assertEquals(
            fakeWeatherTileData.takeLast(fakeWeatherTileData.size - 2), result.takeLast(
                fakeWeatherTileData.size - 2))
    }
}