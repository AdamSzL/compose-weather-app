package com.example.weatherapp.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.data.repository.WeatherRepository
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.error.asUiText
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.location_list.data.repository.saved_locations.SavedLocationsRepository
import com.example.weatherapp.weather.domain.use_cases.DeleteTileUseCase
import com.example.weatherapp.weather.domain.use_cases.MoveTileUseCase
import com.example.weatherapp.weather.domain.use_cases.SaveLayoutInHistoryUseCase
import com.example.weatherapp.weather.presentation.mapper.toWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.mapper.toWeatherTiles
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val locationId: Long,
    private val moveTilesUseCase: MoveTileUseCase,
    private val deleteTileUseCase: DeleteTileUseCase,
    private val saveLayoutInHistoryUseCase: SaveLayoutInHistoryUseCase,
    private val weatherRepository: WeatherRepository,
    private val savedLocationsRepository: SavedLocationsRepository,
): ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState = _weatherState.asStateFlow()

    init {
        fetchCurrentWeatherInfo()
    }

    fun onWeatherScreenEvent(weatherScreenEvent: WeatherScreenEvent) {
        when (weatherScreenEvent) {
            is WeatherScreenEvent.ToggleEditMode -> toggleEditMode(weatherScreenEvent.enabled)
            is WeatherScreenEvent.ToggleDeleteMode -> toggleDeleteMode(weatherScreenEvent.enabled)
            is WeatherScreenEvent.ToggleTilesLock -> toggleTilesLock(weatherScreenEvent.locked)
            is WeatherScreenEvent.DeleteTile -> deleteTile(weatherScreenEvent.tileId)
            is WeatherScreenEvent.MoveTile -> moveTile(weatherScreenEvent.from, weatherScreenEvent.to)
            is WeatherScreenEvent.ShuffleTiles -> shuffleTiles()
            is WeatherScreenEvent.SaveLayout -> saveLayout()
            is WeatherScreenEvent.UndoLayoutChange -> undoLayoutChange()
            is WeatherScreenEvent.RedoLayoutChange -> redoLayoutChange()
            is WeatherScreenEvent.SaveLayoutAndExitEditMode -> saveLayoutAndExitEditMode()
            is WeatherScreenEvent.NavigateBack -> Unit
            is WeatherScreenEvent.ResetMessage -> showMessage(null)
        }
    }

    private fun fetchCurrentWeatherInfo() {
        viewModelScope.launch {
            val location = savedLocationsRepository.getLocationById(locationId)
            when (val getDetailedWeatherResult = weatherRepository.getDetailedWeather(location)) {
                is Result.Success -> {
                    _weatherState.update {
                        it.copy(
                            weatherHeaderInfo = getDetailedWeatherResult.data.toWeatherHeaderInfo(),
                            weatherTileData = getDetailedWeatherResult.data.toWeatherTiles()
                        )
                    }
                }
                is Result.Error -> {
                    _weatherState.update {
                        it.copy(message = getDetailedWeatherResult.error.asUiText())
                    }
                }
            }
        }
    }

    private fun showMessage(message: UiText?) {
        _weatherState.update {
            it.copy(message = message)
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
        // maybe debounce? if somebody moves tiles a lot
    }

    private fun shuffleTiles() {
        _weatherState.update {
            it.copy(weatherTileData = it.weatherTileData.shuffled())
        }
        saveLayoutInHistory()
    }

    private fun saveLayoutInHistory() {
        _weatherState.update {
            val (newHistory, tileDataIndex) = saveLayoutInHistoryUseCase(it.weatherTileDataHistory, it.weatherTileData, it.currentWeatherTileDataIndex)
            it.copy(
                weatherTileDataHistory = newHistory,
                currentWeatherTileDataIndex = tileDataIndex
            )
        }
        saveLayout()
    }

    private fun undoLayoutChange() {
        if (_weatherState.value.currentWeatherTileDataIndex > 0) {
            changeLayoutState(_weatherState.value.currentWeatherTileDataIndex - 1)
        }
        saveLayout()
    }

    private fun redoLayoutChange() {
        if (_weatherState.value.currentWeatherTileDataIndex < _weatherState.value.weatherTileDataHistory.size - 1) {
            changeLayoutState(_weatherState.value.currentWeatherTileDataIndex + 1)
        }
        saveLayout()
    }

    private fun changeLayoutState(index: Int) {
        _weatherState.update {
            it.copy(
                weatherTileData = it.weatherTileDataHistory[index],
                currentWeatherTileDataIndex = index
            )
        }
    }

    private fun saveLayout() {
        viewModelScope.launch {
            saveLayoutOnDevice()
        }
    }

    private suspend fun saveLayoutOnDevice() {
        _weatherState.update {
            it.copy(isSavingLayout = true)
        }
        delay(200)
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