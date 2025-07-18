package com.example.weatherapp.location_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.asUiEvent
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.location_list.data.repository.saved_locations.SavedLocationsRepository
import com.example.weatherapp.location_list.di.RefreshSavedLocationsWeatherBriefUseCase
import com.example.weatherapp.location_list.domain.models.asUiText
import com.example.weatherapp.location_list.domain.use_cases.FetchLocationWeatherBriefUseCase
import com.example.weatherapp.location_search.data.repository.location_permission.LocationPermissionRepository
import com.example.weatherapp.location_search.data.repository.user_location.UserLocationRepository
import com.example.weatherapp.location_search.domain.models.asUiText
import com.example.weatherapp.location_search.domain.use_cases.SaveLocationUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationListViewModel(
    private val userLocationRepository: UserLocationRepository,
    private val fetchLocationWeatherBriefUseCase: FetchLocationWeatherBriefUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val refreshSavedLocationsWeatherBriefUseCase: RefreshSavedLocationsWeatherBriefUseCase,
    private val savedLocationsRepository: SavedLocationsRepository,
    private val locationPermissionRepository: LocationPermissionRepository,
): ViewModel() {

    private val _locationListState = MutableStateFlow(LocationListState())
    val locationsState = _locationListState.asStateFlow()

    init {
        fetchSavedLocations()
        fetchWasLocationPermissionAsked()
    }

    fun onLocationsScreenEvent(locationListScreenEvent: LocationListScreenEvent) {
        when (locationListScreenEvent) {
            is LocationListScreenEvent.DeleteLocation -> deleteLocation(locationListScreenEvent.locationId)
            is LocationListScreenEvent.ShowMessage -> showMessage(locationListScreenEvent.message)
            is LocationListScreenEvent.FetchUserLocation -> fetchUserLocation()
            is LocationListScreenEvent.RefreshSavedLocationsWeatherBrief -> refreshSavedLocationsWeatherBrief()
            is LocationListScreenEvent.LocationPermissionWasDenied -> markLocationPermissionDenied()
            else -> Unit
        }
    }

    private fun fetchSavedLocations() {
        toggleIsLoadingLocations(true)
        viewModelScope.launch {
            fetchLocationWeatherBriefUseCase()
                .collect { locationsWithWeatherBrief ->
                    _locationListState.update {
                        it.copy(locationsWithWeatherBrief = locationsWithWeatherBrief)
                    }
                    toggleIsLoadingLocations(false)
                }
        }
    }

    private fun fetchWasLocationPermissionAsked() {
        viewModelScope.launch {
            locationPermissionRepository.wasLocationPermissionAlreadyDenied().collect { wasLocationPermissionAlreadyDenied ->
                _locationListState.update {
                    it.copy(wasLocationPermissionAlreadyDenied = wasLocationPermissionAlreadyDenied)
                }
            }
        }
    }

    private fun markLocationPermissionDenied() {
        showMessage(UiText.StringResource(R.string.location_permission_required_for_this_feature))
        viewModelScope.launch {
            locationPermissionRepository.markLocationPermissionDenied()
        }
    }

    private fun refreshSavedLocationsWeatherBrief() {
        _locationListState.update {
            it.copy(isRefreshingWeatherBriefs = true)
        }
        viewModelScope.launch {
            val savedLocations = _locationListState.value.locationsWithWeatherBrief.map { it.location }
            val refreshWeatherResult = refreshSavedLocationsWeatherBriefUseCase(savedLocations)
            showMessage(refreshWeatherResult.asUiText())
            delay(500)
            _locationListState.update {
                it.copy(isRefreshingWeatherBriefs = false)
            }
        }
    }

    private fun toggleIsLoadingLocations(isLoading: Boolean) {
        _locationListState.update {
            it.copy(isLoadingLocations = isLoading)
        }
    }

    private fun showMessage(message: UiText?) {
        _locationListState.update {
            it.copy(
                showMessageEvent = message?.asUiEvent { showMessage(null) }
            )
        }
    }

    private fun deleteLocation(locationId: Long) {
        viewModelScope.launch {
            savedLocationsRepository.deleteLocation(locationId)
        }
    }

    private fun fetchUserLocation() {
        viewModelScope.launch {
            when (val locationResult = userLocationRepository.getUserLocation()) {
                is Result.Success -> {
                    saveLocationUseCase(locationResult.data)
                }
                is Result.Error -> {
                    showMessage(locationResult.error.asUiText())
                }
            }
        }
    }

}