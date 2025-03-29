package com.example.weatherapp.locations.presentation.saved_locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.core.data.repository.WeatherRepository
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.error.asUiText
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.locations.data.repository.LocationRepository
import com.example.weatherapp.locations.domain.models.LocationWeatherBrief
import com.example.weatherapp.locations.domain.models.asUiText
import com.example.weatherapp.locations.domain.use_cases.DeleteLocationUseCase
import com.example.weatherapp.locations.domain.use_cases.ReverseGeocodeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel(
    private val deleteLocationUseCase: DeleteLocationUseCase,
    private val reverseGeocodeUseCase: ReverseGeocodeUseCase,
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _locationsState = MutableStateFlow(LocationsState())
    val locationsState = _locationsState.asStateFlow()

    init {
        fetchSavedLocations()
    }

    fun onLocationsScreenEvent(locationsScreenEvent: LocationsScreenEvent) {
        when (locationsScreenEvent) {
            is LocationsScreenEvent.DeleteLocation -> deleteLocation(locationsScreenEvent.locationId)
            is LocationsScreenEvent.ShowSnackbar -> showMessage(locationsScreenEvent.message)
            is LocationsScreenEvent.ResetMessage -> showMessage(null)
            is LocationsScreenEvent.FetchUserLocation -> fetchUserLocation()
            is LocationsScreenEvent.AddMapLocation -> addMapLocation(locationsScreenEvent.coordinates)
            else -> Unit
        }
    }

    private fun fetchSavedLocations() {
        viewModelScope.launch {
            val locations = locationRepository.getSavedLocations()
            val updatedLocations = locations.map { locationWeatherBrief ->
                val briefWeatherResult = weatherRepository.getBriefWeather(locationWeatherBrief.location.coordinates)
                when (briefWeatherResult) {
                    is Result.Success -> {
                        LocationWeatherBrief(
                            id = locationWeatherBrief.id,
                            location = locationWeatherBrief.location,
                            weatherBrief = briefWeatherResult.data,
                        )
                    }
                    is Result.Error -> {
                        showMessage(briefWeatherResult.error.asUiText())
                        locationWeatherBrief
                    }
                }
            }
            _locationsState.update {
                it.copy(locations = updatedLocations)
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
        val locationWeatherBrief = LocationWeatherBrief(location = location, weatherBrief = null)
        _locationsState.update {
            it.copy(
                locations = it.locations + locationWeatherBrief,
                message = message
            )
        }
    }

    private fun deleteLocation(locationId: String) {
        _locationsState.update {
            it.copy(locations = deleteLocationUseCase(it.locations, locationId))
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