package com.example.weatherapp.weather.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.weather.domain.WeatherTileData
import com.example.weatherapp.weather.domain.use_cases.DeleteTileUseCase
import com.example.weatherapp.weather.domain.use_cases.MoveTileUseCase
import com.example.weatherapp.weather.domain.use_cases.ResetLayoutUseCase
import com.example.weatherapp.weather.domain.use_cases.SaveLayoutInHistoryUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val moveTilesUseCase: MoveTileUseCase,
    private val deleteTileUseCase: DeleteTileUseCase,
    private val saveLayoutInHistoryUseCase: SaveLayoutInHistoryUseCase,
    private val resetLayoutUseCase: ResetLayoutUseCase
): ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState = _weatherState.asStateFlow()

    private val autoSaveLayoutChanges = MutableSharedFlow<Unit>(replay = 0)
    private val autoSaveDelay = 2000L

    init {
        viewModelScope.launch {
            autoSaveLayoutChanges
                .debounce(autoSaveDelay)
                .collect {
                    saveLayoutOnDevice()
                }
        }
    }

    fun onWeatherScreenEvent(weatherScreenEvent: WeatherScreenEvent) {
        when (weatherScreenEvent) {
            is WeatherScreenEvent.ToggleEditMode-> toggleEditMode(weatherScreenEvent.enabled)
            is WeatherScreenEvent.ToggleDeleteMode -> toggleDeleteMode(weatherScreenEvent.enabled)
            is WeatherScreenEvent.ToggleTilesLock -> toggleTilesLock(weatherScreenEvent.locked)
            is WeatherScreenEvent.DeleteTile -> deleteTile(weatherScreenEvent.tileId)
            is WeatherScreenEvent.MoveTile -> moveTile(weatherScreenEvent.from, weatherScreenEvent.to)
            is WeatherScreenEvent.ShuffleTiles -> shuffleTiles()
            is WeatherScreenEvent.ToggleAutoSave -> toggleAutoSave(weatherScreenEvent.checked)
            is WeatherScreenEvent.SaveLayout -> saveLayout()
            is WeatherScreenEvent.ResetLayout -> resetLayout()
            is WeatherScreenEvent.UndoLayoutChange -> undoLayoutChange()
            is WeatherScreenEvent.RedoLayoutChange -> redoLayoutChange()
            is WeatherScreenEvent.SaveLayoutAndExitEditMode -> saveLayoutAndExitEditMode()
            else -> Unit
        }
    }

    fun setWeatherTiles(tiles: List<WeatherTileData>) {
        _weatherState.update {
            it.copy(weatherTileData = tiles)
        }
    }

    private fun toggleEditMode(enabled: Boolean) {
        if (!enabled) {
            toggleDeleteMode(false)
        }
        _weatherState.update {
            it.copy(isEditModeEnabled = enabled)
        }
    }

    private fun toggleDeleteMode(enabled: Boolean) {
        _weatherState.update {
            it.copy(isDeleteModeEnabled = enabled)
        }
    }

    private fun toggleTilesLock(locked: Boolean) {
        _weatherState.update {
            it.copy(areTilesLocked = locked)
        }
    }

    private fun deleteTile(tileId: String) {
        _weatherState.update {
            it.copy(weatherTileData = deleteTileUseCase(it.weatherTileData, tileId))
        }
        saveLayoutInHistory()
    }

    private fun moveTile(from: Int, to: Int) {
        _weatherState.update {
            it.copy(weatherTileData = moveTilesUseCase(it.weatherTileData, from, to))
        }
        saveLayoutInHistory()
        // debounce? Jak ktoś kilka razy przesuwa to wtedy lipa
    }

    private fun shuffleTiles() {
        _weatherState.update {
            it.copy(weatherTileData = it.weatherTileData.shuffled())
        }
        saveLayoutInHistory()
    }

    private fun toggleAutoSave(checked: Boolean) {
        _weatherState.update {
            it.copy(isAutoSaveEnabled = checked)
        }
    }

    private fun saveLayoutInHistory() {
        _weatherState.update {
            val (newHistory, tileDataIndex) = saveLayoutInHistoryUseCase(it.weatherTileDataHistory, it.weatherTileData, it.currentWeatherTileDataIndex)
            it.copy(
                weatherTileDataHistory = newHistory,
                currentWeatherTileDataIndex = tileDataIndex
            )
        }
        saveLayoutIfAutoSaveEnabled()
    }

    private fun resetLayout() {
        _weatherState.update {
            val (newHistory, tileData, tileDataIndex) = resetLayoutUseCase(it.weatherTileDataHistory, it.currentWeatherTileDataIndex)
            it.copy(
                weatherTileDataHistory = newHistory,
                weatherTileData = tileData,
                currentWeatherTileDataIndex = tileDataIndex
            )
        }
        saveLayoutIfAutoSaveEnabled()
    }

    private fun undoLayoutChange() {
        if (_weatherState.value.currentWeatherTileDataIndex > 0) {
            changeLayoutState(_weatherState.value.currentWeatherTileDataIndex - 1)
        }
        saveLayoutIfAutoSaveEnabled()
    }

    private fun redoLayoutChange() {
        if (_weatherState.value.currentWeatherTileDataIndex < _weatherState.value.weatherTileDataHistory.size - 1) {
            changeLayoutState(_weatherState.value.currentWeatherTileDataIndex + 1)
        }
        saveLayoutIfAutoSaveEnabled()
    }

    private fun changeLayoutState(index: Int) {
        _weatherState.update {
            it.copy(
                weatherTileData = it.weatherTileDataHistory[index],
                currentWeatherTileDataIndex = index
            )
        }
    }

    private fun saveLayoutIfAutoSaveEnabled() {
        if (_weatherState.value.isAutoSaveEnabled) {
            saveLayout()
        }
    }

    private fun saveLayout() {
        viewModelScope.launch {
            autoSaveLayoutChanges.emit(Unit)
        }
    }

    private suspend fun saveLayoutOnDevice() {
        _weatherState.update {
            it.copy(isSavingLayout = true)
        }
        delay(2000)
        _weatherState.update {
            it.copy(isSavingLayout = false)
        }
    }

    private fun saveLayoutAndExitEditMode() {
        viewModelScope.launch {
            saveLayoutOnDevice()
            toggleEditMode(false)
        }
    }
}