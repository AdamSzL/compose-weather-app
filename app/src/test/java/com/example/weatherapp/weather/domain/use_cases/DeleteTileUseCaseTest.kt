package com.example.weatherapp.weather.domain.use_cases

import com.example.weatherapp.weather.presentation.fake.fakeWeatherTileData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteTileUseCaseTest {

    private lateinit var deleteTileUseCase: DeleteTileUseCase

    @Before
    fun setUp() {
        deleteTileUseCase = DeleteTileUseCase()
    }

    @Test
    fun deleteTileUseCase_DeleteTile_RemovesTileFromList() {
        val tileIdToDelete = fakeWeatherTileData.first().tileId
        val tiles = deleteTileUseCase(fakeWeatherTileData, tileIdToDelete)
        assertTrue(tiles.none { it.tileId == tileIdToDelete })
        assertEquals(fakeWeatherTileData.size - 1, tiles.size)
    }

}