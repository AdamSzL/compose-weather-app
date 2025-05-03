package com.example.weatherapp.location_search.presentation.place_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.asUiEvent
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.location_search.data.repository.place_search.LocationSearchRepository
import com.example.weatherapp.location_search.domain.models.asUiText
import com.example.weatherapp.location_search.domain.use_cases.FetchPlaceSuggestionsUseCase
import com.example.weatherapp.location_search.domain.use_cases.SaveLocationUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class LocationSearchViewModel(
    private val fetchPlaceSuggestionsUseCase: FetchPlaceSuggestionsUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val locationSearchRepository: LocationSearchRepository,
) : ViewModel() {

    private val _locationSearchState = MutableStateFlow(LocationSearchState())
    val locationSearchState = _locationSearchState.asStateFlow()

    init {
        viewModelScope.launch {
            _locationSearchState
                .map { it.locationSearchQuery }
                .debounce(DEBOUNCE_MILLIS)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest { query ->
                    fetchSuggestions(query)
                }
        }
    }

    private suspend fun fetchSuggestions(query: String) {
        _locationSearchState.update {
            it.copy(isLoadingSuggestions = true)
        }
        val (placeSuggestions, message) = fetchPlaceSuggestionsUseCase(query)
        showMessage(message)
        _locationSearchState.update {
            it.copy(
                placeSuggestions = placeSuggestions,
                isLoadingSuggestions = false
            )
        }
    }

    fun onLocationSearchScreenEvent(event: LocationSearchScreenEvent) {
        when (event) {
            is LocationSearchScreenEvent.LocationSearchQueryChanged -> updateLocationSearchQuery(event.query)
            is LocationSearchScreenEvent.PlaceSelected -> fetchPlaceDetailsAndSaveLocation(event.placeId)
            else -> Unit
        }
    }

    private fun updateLocationSearchQuery(query: String) {
        _locationSearchState.update {
            it.copy(locationSearchQuery = query)
        }
    }

    private fun fetchPlaceDetailsAndSaveLocation(placeId: String) {
        _locationSearchState.update {
            it.copy(currentlySavingPlaceId = placeId)
        }
        viewModelScope.launch {
            when (val locationResult = locationSearchRepository.fetchLocationFromPlaceId(placeId)) {
                is Result.Success -> {
                    saveLocation(locationResult.data)
                }
                is Result.Error -> {
                    showMessage(locationResult.error.asUiText())
                    resetCurrentlySavingPlaceId()
                }
            }
        }
    }

    private suspend fun saveLocation(location: GeoPoint) {
        when (val saveLocationResult = saveLocationUseCase(location)) {
            is Result.Success -> {
                _locationSearchState.update {
                    it.copy(navigateBackEvent = Unit.asUiEvent {})
                }
            }
            is Result.Error -> {
                showMessage(saveLocationResult.error.asUiText())
            }
        }
        resetCurrentlySavingPlaceId()
    }

    private fun showMessage(message: UiText?) {
        _locationSearchState.update {
            it.copy(
                showMessageEvent = message?.asUiEvent { showMessage(null) }
            )
        }
    }

    private fun resetCurrentlySavingPlaceId() {
        _locationSearchState.update {
            it.copy(currentlySavingPlaceId = null)
        }
    }

    companion object {
        private const val DEBOUNCE_MILLIS = 1000L
    }

}