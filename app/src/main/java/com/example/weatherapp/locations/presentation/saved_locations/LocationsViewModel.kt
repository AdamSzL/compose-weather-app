package com.example.weatherapp.locations.presentation.saved_locations

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.locations.data.repository.LocationRepository
import com.example.weatherapp.locations.domain.SavedLocation
import com.example.weatherapp.locations.domain.asUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel(
    private val locationRepository: LocationRepository,
): ViewModel() {

    private val _locationsState = MutableStateFlow(LocationsState())
    val locationsState = _locationsState.asStateFlow()

    fun onLocationsScreenEvent(locationsScreenEvent: LocationsScreenEvent) {
        when (locationsScreenEvent) {
            is LocationsScreenEvent.DeleteLocation -> deleteLocation(locationsScreenEvent.locationId)
            is LocationsScreenEvent.SetLocationAsActive -> setLocationAsActive(locationsScreenEvent.locationId)
            is LocationsScreenEvent.ShowSnackbar -> showMessage(locationsScreenEvent.message)
            is LocationsScreenEvent.ResetMessage -> showMessage(null)
            is LocationsScreenEvent.FetchUserLocation -> fetchUserLocation()
            is LocationsScreenEvent.AddMapLocation -> addMapLocation(locationsScreenEvent.latLng)
            else -> Unit
        }
    }

    private fun showMessage(message: UiText?) {
        _locationsState.update {
            it.copy(message = message)
        }
    }

    private fun addMapLocation(latLng: Pair<Double, Double>) {
        val location = Location("")
        location.latitude = latLng.first
        location.longitude = latLng.second
        fetchAddressByLocationAndAddLocation(location)
    }

    private fun addLocation(location: SavedLocation) {
        _locationsState.update {
            it.copy(
                savedLocations = it.savedLocations + location,
                message = UiText.StringResource(R.string.location_successfully_added)
            )
        }
    }

    private fun deleteLocation(locationId: String) {
        _locationsState.update { state ->
            state.copy(
                savedLocations = state.savedLocations.filter { it.id != locationId }
            )
        }
    }

    private fun setLocationAsActive(locationId: String) {
        _locationsState.update { state ->
            state.copy(selectedLocationId = locationId)
        }
    }

    private fun fetchUserLocation() {
        viewModelScope.launch {
            when (val locationResult = locationRepository.fetchUserLocation()) {
                is Result.Success -> {
                    fetchAddressByLocationAndAddLocation(locationResult.data)
                }
                is Result.Error -> {
                    _locationsState.update {
                        it.copy(message = locationResult.error.asUiText())
                    }
                }
            }
        }
    }

    private fun fetchAddressByLocationAndAddLocation(location: Location) {
        viewModelScope.launch {
            val (locationName, locationCountry) = when (val addressResult = locationRepository.getAddressFromLocation(location)) {
                is Result.Success -> {
                    addressResult.data
                }
                is Result.Error -> {
                    Pair("Unknown Location", null)
                }
            }
            val locationToAdd = SavedLocation(
                name = locationName,
                latitude = location.latitude,
                longitude = location.longitude,
                country = locationCountry
            )
            addLocation(locationToAdd)
        }
    }
}