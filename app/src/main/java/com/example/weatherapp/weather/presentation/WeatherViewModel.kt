package com.example.weatherapp.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.data.repository.WeatherRepository
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.error.asUiText
import com.example.weatherapp.core.domain.model.DetailedWeather
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.location_list.data.repository.saved_locations.SavedLocationsRepository
import com.example.weatherapp.weather.data.repository.TileLayoutRepository
import com.example.weatherapp.weather.domain.use_cases.DeleteTileUseCase
import com.example.weatherapp.weather.domain.use_cases.MoveTileUseCase
import com.example.weatherapp.weather.domain.use_cases.SaveLayoutInHistoryUseCase
import com.example.weatherapp.weather.presentation.mapper.toWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.mapper.toWeatherTiles
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
    private val tileLayoutRepository: TileLayoutRepository,
): ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState = _weatherState.asStateFlow()

    lateinit var detailedWeather: DetailedWeather

    init {
        fetchCurrentWeatherInfo()
    }

    fun onWeatherScreenEvent(weatherScreenEvent: WeatherScreenEvent) {
        when (weatherScreenEvent) {
            is WeatherScreenEvent.ToggleEditMode -> toggleEditMode(weatherScreenEvent.enabled)
            is WeatherScreenEvent.ToggleDeleteMode -> toggleDeleteMode(weatherScreenEvent.enabled)
            is WeatherScreenEvent.ToggleTilesLock -> toggleTilesLock(weatherScreenEvent.locked)
            is WeatherScreenEvent.DeleteTile -> updateTiles(TileAction.Delete(weatherScreenEvent.tileId))
            is WeatherScreenEvent.MoveTile -> updateTiles(TileAction.Move(weatherScreenEvent.from, weatherScreenEvent.to))
            is WeatherScreenEvent.ShuffleTiles -> updateTiles(TileAction.Shuffle)
            is WeatherScreenEvent.ResetLayout -> resetLayout()
            is WeatherScreenEvent.UndoLayoutChange -> undoLayoutChange()
            is WeatherScreenEvent.RedoLayoutChange -> redoLayoutChange()
            is WeatherScreenEvent.SaveLayoutAndExitEditMode -> saveLayoutAndExitEditMode()
            is WeatherScreenEvent.NavigateBack -> Unit
            is WeatherScreenEvent.ResetMessage -> showMessage(null)
        }
    }

    private fun fetchCurrentWeatherInfo() {
        toggleIsLoadingWeather(true)
        viewModelScope.launch {
            val location = savedLocationsRepository.getLocationById(locationId)
            when (val getDetailedWeatherResult = weatherRepository.getDetailedWeather(location)) {
                is Result.Success -> {
                    val savedTileOrder = tileLayoutRepository.loadTileOrder()
                    detailedWeather = getDetailedWeatherResult.data
                    val weatherTileData = detailedWeather.toWeatherTiles(savedTileOrder)
                    _weatherState.update {
                        it.copy(
                            weatherInfo = WeatherInfo(
                                location = location,
                                timezone = detailedWeather.timezone,
                                hourlyForecast = detailedWeather.hourlyForecast,
                                dailyForecast = detailedWeather.dailyForecast,
                                weatherHeaderInfo = detailedWeather.toWeatherHeaderInfo(),
                                weatherTileData = weatherTileData,
                            ),
                            weatherTileDataHistory = listOf(weatherTileData)
                        )
                    }
                }
                is Result.Error -> {
                    _weatherState.update {
                        it.copy(message = getDetailedWeatherResult.error.asUiText())
                    }
                }
            }
            toggleIsLoadingWeather(false)
        }
    }

    private fun showMessage(message: UiText?) {
        _weatherState.update {
            it.copy(message = message)
        }
    }

    private fun toggleIsLoadingWeather(isLoading: Boolean) {
        _weatherState.update {
            it.copy(isLoading = isLoading)
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

    private fun toggleIsSavingLayout(isSaving: Boolean) {
        _weatherState.update {
            it.copy(isSavingLayout = isSaving)
        }
    }

    private fun updateTiles(action: TileAction) {
        val tileDataHistory = _weatherState.value.weatherTileDataHistory
        val currentTileData = _weatherState.value.weatherInfo!!.weatherTileData
        val newTileData = when (action) {
            is TileAction.Delete -> deleteTileUseCase(currentTileData, action.tileId)
            is TileAction.Move -> moveTilesUseCase(currentTileData, action.from, action.to)
            is TileAction.Shuffle -> currentTileData.shuffled()
            is TileAction.JumpToHistoryIndex -> tileDataHistory[action.index]
        }
        _weatherState.update { state ->
            state.weatherInfo?.let { info ->
                state.copy(
                    weatherInfo = info.copy(
                        weatherTileData = newTileData
                    )
                )
            } ?: state
        }
        saveLayoutInHistory()
    }

    private fun saveLayoutInHistory() {
        _weatherState.update {
            val (newHistory, tileDataIndex) = saveLayoutInHistoryUseCase(it.weatherTileDataHistory, it.weatherInfo!!.weatherTileData, it.currentWeatherTileDataIndex)
            it.copy(
                weatherTileDataHistory = newHistory,
                currentWeatherTileDataIndex = tileDataIndex
            )
        }
    }

    private fun resetLayout() {
        viewModelScope.launch {
            _weatherState.update {
                it.copy(
                    weatherInfo = it.weatherInfo!!.copy(
                        weatherTileData = detailedWeather.toWeatherTiles()
                    )
                )
            }
            tileLayoutRepository.resetTileOrder()
            saveLayoutInHistory()
        }
    }

    private fun undoLayoutChange() {
        if (_weatherState.value.currentWeatherTileDataIndex > 0) {
            changeLayoutState(_weatherState.value.currentWeatherTileDataIndex - 1)
        }
    }

    private fun redoLayoutChange() {
        if (_weatherState.value.currentWeatherTileDataIndex < _weatherState.value.weatherTileDataHistory.size - 1) {
            changeLayoutState(_weatherState.value.currentWeatherTileDataIndex + 1)
        }
    }

    private fun changeLayoutState(index: Int) {
        updateTiles(TileAction.JumpToHistoryIndex(index))
        _weatherState.update {
            it.copy(currentWeatherTileDataIndex = index)
        }
    }

    private fun saveLayoutAndExitEditMode() {
        if (_weatherState.value.isSavingLayout.not()) {
            viewModelScope.launch {
                toggleIsSavingLayout(true)
                tileLayoutRepository.saveTileOrder(_weatherState.value.weatherInfo!!.weatherTileData)
                toggleIsSavingLayout(false)
                toggleEditMode(false)
            }
        }
    }
}