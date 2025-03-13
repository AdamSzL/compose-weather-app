package com.example.weatherapp.locations.presentation.saved_locations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.locations.data.repository.LocationRepository
import com.example.weatherapp.locations.domain.models.GeoLocation
import com.example.weatherapp.locations.domain.models.GeoPoint
import com.example.weatherapp.locations.domain.models.asUiText
import com.example.weatherapp.locations.domain.use_cases.DeleteLocationUseCase
import com.example.weatherapp.locations.domain.use_cases.ReverseGeocodeUseCase
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeUserLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel(
    private val deleteLocationUseCase: DeleteLocationUseCase,
    private val reverseGeocodeUseCase: ReverseGeocodeUseCase,
    private val locationRepository: LocationRepository
): ViewModel() {

    private val _locationsState = MutableStateFlow(LocationsState())
    val locationsState = _locationsState.asStateFlow()

    init {
        fetchSavedLocations()
    }

    fun onLocationsScreenEvent(locationsScreenEvent: LocationsScreenEvent) {
        when (locationsScreenEvent) {
            is LocationsScreenEvent.DeleteLocation -> deleteLocation(locationsScreenEvent.locationId)
            is LocationsScreenEvent.SetLocationAsActive -> setLocationAsActive(locationsScreenEvent.locationId)
            is LocationsScreenEvent.ShowSnackbar -> showMessage(locationsScreenEvent.message)
            is LocationsScreenEvent.ResetMessage -> showMessage(null)
            is LocationsScreenEvent.FetchUserLocation -> fetchUserLocation()
            is LocationsScreenEvent.AddMapLocation -> addMapLocation(locationsScreenEvent.coordinates)
            else -> Unit
        }
    }

    private fun fetchSavedLocations() {
        viewModelScope.launch {
            val savedLocations = locationRepository.getSavedLocations()
            _locationsState.update {
                it.copy(savedLocations = savedLocations)
            }
        }
    }

    private fun showMessage(message: UiText?) {
        _locationsState.update {
            it.copy(message = message)
        }
    }

    private fun addMapLocation(coordinates: GeoPoint) {
        viewModelScope.launch {
            val location = reverseGeocode(coordinates)
            addLocation(location)
        }
    }

    private fun addLocation(location: GeoLocation, shouldIncludeMessage: Boolean = false) {
        val message = if (shouldIncludeMessage) UiText.StringResource(R.string.location_successfully_added) else null
        _locationsState.update {
            it.copy(
                savedLocations = it.savedLocations + location,
                message = message
            )
        }
    }

    private fun deleteLocation(locationId: String) {
        _locationsState.update {
            it.copy(savedLocations = deleteLocationUseCase(it.savedLocations, locationId))
        }
    }

    private fun setLocationAsActive(locationId: String) {
        _locationsState.update {
            it.copy(selectedLocationId = locationId)
        }
    }

    private fun fetchUserLocation() {
        viewModelScope.launch {
            when (val locationResult = locationRepository.fetchUserLocation()) {
                is Result.Success -> {
                    val location = reverseGeocode(locationResult.data)
                    addLocation(location)
                }
                is Result.Error -> {
                    showMessage(locationResult.error.asUiText())
                }
            }
        }
    }

    private suspend fun reverseGeocode(coordinates: GeoPoint): GeoLocation {
        return reverseGeocodeUseCase(coordinates)
    }
}