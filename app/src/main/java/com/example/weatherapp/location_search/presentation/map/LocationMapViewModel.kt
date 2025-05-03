package com.example.weatherapp.location_search.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.asUiEvent
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.location_search.domain.models.asUiText
import com.example.weatherapp.location_search.domain.use_cases.SaveLocationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationMapViewModel(
    private val saveLocationUseCase: SaveLocationUseCase,
) : ViewModel() {

    private val _locationMapState = MutableStateFlow(LocationMapState())
    val locationMapState = _locationMapState.asStateFlow()

    init {

    }

    fun onLocationMapScreenEvent(event: LocationMapScreenEvent) {
        when (event) {
            is LocationMapScreenEvent.SelectLocation -> updateSelectedLocation(event.location)
            is LocationMapScreenEvent.SaveSelectedLocation -> saveSelectedLocation()
            else -> Unit
        }
    }

    private fun updateSelectedLocation(location: GeoPoint) {
        _locationMapState.update {
            it.copy(
                selectedLocation = location
            )
        }
    }

    private fun saveSelectedLocation() {
        val selectedPoint = _locationMapState.value.selectedLocation ?: return
        _locationMapState.update {
            it.copy(isSavingLocation = true)
        }
        viewModelScope.launch {
            when (val saveLocationResult = saveLocationUseCase(selectedPoint)) {
                is Result.Success -> {
                    _locationMapState.update {
                        it.copy(navigateBackEvent = Unit.asUiEvent {})
                    }
                }
                is Result.Error -> {
                    showMessage(saveLocationResult.error.asUiText())
                }
            }
            resetSavingLocation()
        }
    }

    private fun showMessage(message: UiText?) {
        _locationMapState.update {
            it.copy(
                showMessageEvent = message?.asUiEvent { showMessage(null) }
            )
        }
    }

    private fun resetSavingLocation() {
        _locationMapState.update {
            it.copy(isSavingLocation = false)
        }
    }

}