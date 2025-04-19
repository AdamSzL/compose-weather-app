package com.example.weatherapp.location_search.presentation.place_search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.location_list.data.repository.SavedLocationsRepository
import com.example.weatherapp.location_search.data.repository.place_search.LocationSearchRepository
import com.example.weatherapp.location_search.domain.models.LocationSearchError
import com.example.weatherapp.location_search.domain.models.PlaceSuggestion
import com.example.weatherapp.location_search.domain.models.asUiText
import com.example.weatherapp.location_search.domain.use_cases.FetchPlaceSuggestionsUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
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
        _locationSearchState.update {
            it.copy(
                placeSuggestions = placeSuggestions,
                message = message,
                isLoadingSuggestions = false
            )
        }
    }

    fun onLocationSearchScreenEvent(event: LocationSearchScreenEvent) {
        when (event) {
            is LocationSearchScreenEvent.LocationSearchQueryChanged -> updateLocationSearchQuery(event.query)
            else -> Unit
        }
    }

    private fun updateLocationSearchQuery(query: String) {
        _locationSearchState.update {
            it.copy(locationSearchQuery = query)
        }
    }

    companion object {
        private const val DEBOUNCE_MILLIS = 1000L
    }

}