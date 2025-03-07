package com.example.weatherapp.weather.presentation.weather

import com.example.weatherapp.weather.domain.WeatherTileData
import com.example.weatherapp.weather.domain.use_cases.DeleteTileUseCase
import com.example.weatherapp.weather.domain.use_cases.MoveTileUseCase
import com.example.weatherapp.weather.domain.use_cases.ResetLayoutUseCase
import com.example.weatherapp.weather.domain.use_cases.SaveLayoutInHistoryUseCase
import com.example.weatherapp.weather.presentation.weather.fake.fakeWeatherTileData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {

//    @get:Rule
//    val testDispatcherRule = TestCoroutineRule()

    private lateinit var viewModel: WeatherViewModel
    private lateinit var moveTileUseCase: MoveTileUseCase
    private lateinit var deleteTileUseCase: DeleteTileUseCase
    private lateinit var saveLayoutInHistoryUseCase: SaveLayoutInHistoryUseCase
    private lateinit var resetLayoutUseCase: ResetLayoutUseCase

    @Before
    fun setUp() {
        moveTileUseCase = MoveTileUseCase()
        deleteTileUseCase = DeleteTileUseCase()
        saveLayoutInHistoryUseCase = SaveLayoutInHistoryUseCase()
        resetLayoutUseCase = ResetLayoutUseCase()
        viewModel = WeatherViewModel(moveTileUseCase, deleteTileUseCase, saveLayoutInHistoryUseCase, resetLayoutUseCase)
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ToggleAutoSave(false))
        viewModel.setWeatherTiles(fakeWeatherTileData)
    }

    @Test
    fun weatherViewModel_SetTileData_UpdatesTileDataState() {
        assertEquals(fakeWeatherTileData.size, viewModel.weatherState.value.weatherTileData.size)
        assertEquals(fakeWeatherTileData, viewModel.weatherState.value.weatherTileData)
    }

    @Test
    fun weatherViewModel_ToggleAutoSave_UpdatesAutoSaveState() {
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ToggleAutoSave(true))
        assertTrue(viewModel.weatherState.value.isAutoSaveEnabled)

        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ToggleAutoSave(false))
        assertFalse(viewModel.weatherState.value.isAutoSaveEnabled)
    }

    @Test
    fun weatherViewModel_ToggleEditMode_UpdatesEditModeState() {
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ToggleEditMode(true))
        assertTrue(viewModel.weatherState.value.isEditModeEnabled)

        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ToggleEditMode(false))
        assertFalse(viewModel.weatherState.value.isEditModeEnabled)
    }

    @Test
    fun weatherViewModel_ToggleDeleteMode_UpdatesDeleteModeState() {
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ToggleDeleteMode(true))
        assertTrue(viewModel.weatherState.value.isDeleteModeEnabled)

        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ToggleDeleteMode(false))
        assertFalse(viewModel.weatherState.value.isDeleteModeEnabled)
    }

    @Test
    fun weatherViewModel_ToggleTilesLock_UpdatesTilesLockState() {
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ToggleTilesLock(true))
        assertTrue(viewModel.weatherState.value.areTilesLocked)

        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ToggleTilesLock(false))
        assertFalse(viewModel.weatherState.value.areTilesLocked)
    }

    @Test
    fun weatherViewModel_DeleteTile_RemovesTileFromList() {
        val tileIdsToDelete = fakeWeatherTileData.take(2).map { it.tileId }
        tileIdsToDelete.forEach { tileId ->
            viewModel.onWeatherScreenEvent(WeatherScreenEvent.DeleteTile(tileId))
        }
        assertTrue(viewModel.weatherState.value.weatherTileData.none { it.tileId in tileIdsToDelete })
        assertEquals(fakeWeatherTileData.size - tileIdsToDelete.size, viewModel.weatherState.value.weatherTileData.size)
    }

    @Test
    fun weatherViewModel_MoveTile_CorrectlySwapsTiles() {
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.MoveTile(from = 0, to = 1))
        assertEquals(fakeWeatherTileData[1], viewModel.weatherState.value.weatherTileData[0])
        assertEquals(fakeWeatherTileData[0], viewModel.weatherState.value.weatherTileData[1])
        assertEquals(fakeWeatherTileData.takeLast(fakeWeatherTileData.size - 2), viewModel.weatherState.value.weatherTileData.takeLast(fakeWeatherTileData.size - 2))
    }

    @Test
    fun weatherViewModel_MoveTileToTheSamePosition_DoesNothing() {
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.MoveTile(from = 0, to = 0))
        assertEquals(fakeWeatherTileData, viewModel.weatherState.value.weatherTileData)
    }

    @Test
    fun weatherViewModel_ShuffleTiles_ShufflesTilesInTheList() {
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ShuffleTiles)
        assertEquals(fakeWeatherTileData.size, viewModel.weatherState.value.weatherTileData.size)
        assertTrue(fakeWeatherTileData.containsAll(viewModel.weatherState.value.weatherTileData) && viewModel.weatherState.value.weatherTileData.containsAll(fakeWeatherTileData))
    }

    @Test
    fun weatherViewModel_SaveLayoutInHistory_LayoutIsAddedToHistory() {
        assertEquals(1, viewModel.weatherState.value.weatherTileDataHistory.size)
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ShuffleTiles)
        assertEquals(2, viewModel.weatherState.value.weatherTileDataHistory.size)
        val tileIdToDelete = fakeWeatherTileData.first().tileId
        val currentTileData = viewModel.weatherState.value.weatherTileData
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.DeleteTile(tileIdToDelete))
        assertEquals(2, viewModel.weatherState.value.currentWeatherTileDataIndex)
        assertEquals(3, viewModel.weatherState.value.weatherTileDataHistory.size)
        assertEquals(viewModel.weatherState.value.weatherTileDataHistory.last(), currentTileData.filter { it.tileId != tileIdToDelete })
    }

    @Test
    fun weatherViewModel_SaveLayoutInHistoryWhenLayoutIsNotLatest_CorrectlyUpdatesHistory() {
        assertEquals(1, viewModel.weatherState.value.weatherTileDataHistory.size)
        repeat(5) {
            viewModel.onWeatherScreenEvent(WeatherScreenEvent.ShuffleTiles)
        }
        assertEquals(6, viewModel.weatherState.value.weatherTileDataHistory.size)
        assertEquals(5, viewModel.weatherState.value.currentWeatherTileDataIndex)
        repeat(3) {
            viewModel.onWeatherScreenEvent(WeatherScreenEvent.UndoLayoutChange)
        }
        assertEquals(2, viewModel.weatherState.value.currentWeatherTileDataIndex)
        val historyBefore = viewModel.weatherState.value.weatherTileDataHistory
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ShuffleTiles)
        assertEquals(3, viewModel.weatherState.value.currentWeatherTileDataIndex)
        assertEquals(4, viewModel.weatherState.value.weatherTileDataHistory.size)
        assertEquals(viewModel.weatherState.value.weatherTileDataHistory.last(), viewModel.weatherState.value.weatherTileData)
        assertEquals(historyBefore.take(3), viewModel.weatherState.value.weatherTileDataHistory.take(3))
    }

    @Test
    fun weatherViewModel_UndoLayoutChangeWhenNotPossible_DoesNothing() {
        assertEquals(1, viewModel.weatherState.value.weatherTileDataHistory.size)
        assertEquals(0, viewModel.weatherState.value.currentWeatherTileDataIndex)
        val layoutBefore = viewModel.weatherState.value.weatherTileData
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.UndoLayoutChange)
        assertEquals(1, viewModel.weatherState.value.weatherTileDataHistory.size)
        assertEquals(0, viewModel.weatherState.value.currentWeatherTileDataIndex)
        assertEquals(layoutBefore, viewModel.weatherState.value.weatherTileData)
    }

    @Test
    fun weatherViewModel_UndoLayoutChangeWhenPossible_ChangesLayout() {
        val layoutBefore = viewModel.weatherState.value.weatherTileData
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.DeleteTile(fakeWeatherTileData.first().tileId))
        assertEquals(1, viewModel.weatherState.value.currentWeatherTileDataIndex)
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.UndoLayoutChange)
        assertEquals(2, viewModel.weatherState.value.weatherTileDataHistory.size)
        assertEquals(0, viewModel.weatherState.value.currentWeatherTileDataIndex)
        assertEquals(layoutBefore, viewModel.weatherState.value.weatherTileData)
    }

    @Test
    fun weatherViewModel_RedoLayoutChangeWhenNotPossible_DoesNothing() {
        assertEquals(1, viewModel.weatherState.value.weatherTileDataHistory.size)
        assertEquals(0, viewModel.weatherState.value.currentWeatherTileDataIndex)
        val layoutBefore = viewModel.weatherState.value.weatherTileData
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.RedoLayoutChange)
        assertEquals(1, viewModel.weatherState.value.weatherTileDataHistory.size)
        assertEquals(0, viewModel.weatherState.value.currentWeatherTileDataIndex)
        assertEquals(layoutBefore, viewModel.weatherState.value.weatherTileData)
    }

    @Test
    fun weatherViewModel_RedoLayoutChangeWhenPossible_ChangesLayout() {
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.DeleteTile(fakeWeatherTileData.first().tileId))
        val layoutBefore = viewModel.weatherState.value.weatherTileData
        assertEquals(1, viewModel.weatherState.value.currentWeatherTileDataIndex)
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.UndoLayoutChange)
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.RedoLayoutChange)
        assertEquals(2, viewModel.weatherState.value.weatherTileDataHistory.size)
        assertEquals(1, viewModel.weatherState.value.currentWeatherTileDataIndex)
        assertEquals(layoutBefore, viewModel.weatherState.value.weatherTileData)
    }

    @Test
    fun weatherViewModel_ResetLayoutWhenCurrentLayoutIsLatest_CorrectlyUpdatesState() {
        val layoutBefore = viewModel.weatherState.value.weatherTileData
        repeat(5) {
            viewModel.onWeatherScreenEvent(WeatherScreenEvent.ShuffleTiles)
        }
        assertEquals(6, viewModel.weatherState.value.weatherTileDataHistory.size)
        viewModel.onWeatherScreenEvent(WeatherScreenEvent.ResetLayout)
        assertEquals(7, viewModel.weatherState.value.weatherTileDataHistory.size)
        assertEquals(6, viewModel.weatherState.value.currentWeatherTileDataIndex)
        assertEquals(layoutBefore, viewModel.weatherState.value.weatherTileData)
    }
}