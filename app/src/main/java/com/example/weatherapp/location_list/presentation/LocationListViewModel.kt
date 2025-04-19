package com.example.weatherapp.location_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.core.data.repository.WeatherRepository
import com.example.weatherapp.core.domain.Result
import com.example.weatherapp.core.domain.error.asUiText
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.location_list.data.repository.SavedLocationsRepository
import com.example.weatherapp.location_list.domain.models.LocationWeatherBrief
import com.example.weatherapp.location_search.domain.models.asUiText
import com.example.weatherapp.location_list.domain.use_cases.DeleteLocationUseCase
import com.example.weatherapp.location_list.domain.use_cases.ReverseGeocodeUseCase
import com.example.weatherapp.location_search.data.repository.user_location.UserLocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationListViewModel(
    private val deleteLocationUseCase: DeleteLocationUseCase,
    private val reverseGeocodeUseCase: ReverseGeocodeUseCase,
    private val savedLocationsRepository: SavedLocationsRepository,
    private val weatherRepository: WeatherRepository,
    private val userLocationRepository: UserLocationRepository,
): ViewModel() {

    private val _locationListState = MutableStateFlow(LocationListState())
    val locationsState = _locationListState.asStateFlow()

    init {
        fetchSavedLocations()
    }

    fun onLocationsScreenEvent(locationListScreenEvent: LocationListScreenEvent) {
        when (locationListScreenEvent) {
            is LocationListScreenEvent.DeleteLocation -> deleteLocation(locationListScreenEvent.locationId)
            is LocationListScreenEvent.ShowSnackbar -> showMessage(locationListScreenEvent.message)
            is LocationListScreenEvent.ResetMessage -> showMessage(null)
            is LocationListScreenEvent.FetchUserLocation -> fetchUserLocation()
            is LocationListScreenEvent.AddMapLocation -> addMapLocation(locationListScreenEvent.coordinates)
            else -> Unit
        }
    }

    private fun fetchSavedLocations() {
        viewModelScope.launch {
            val locations = savedLocationsRepository.getSavedLocations()
//            val updatedLocations = locations.map { locationWeatherBrief ->
//                val briefWeatherResult = weatherRepository.getBriefWeather(locationWeatherBrief.location.coordinates)
//                when (briefWeatherResult) {
//                    is Result.Success -> {
//                        LocationWeatherBrief(
//                            id = locationWeatherBrief.id,
//                            location = locationWeatherBrief.location,
//                            weatherBrief = briefWeatherResult.data,
//                        )
//                    }
//                    is Result.Error -> {
//                        showMessage(briefWeatherResult.error.asUiText())
//                        locationWeatherBrief
//                    }
//                }
//            }
            _locationListState.update {
                it.copy(locations = locations)
            }
        }
    }

    private fun showMessage(message: UiText?) {
        _locationListState.update {
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
        _locationListState.update {
            it.copy(
                locations = it.locations + locationWeatherBrief,
                message = message
            )
        }
    }

    private fun deleteLocation(locationId: String) {
        _locationListState.update {
            it.copy(locations = deleteLocationUseCase(it.locations, locationId))
        }
    }

    private fun fetchUserLocation() {
        viewModelScope.launch {
            when (val locationResult = userLocationRepository.getUserLocation()) {
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