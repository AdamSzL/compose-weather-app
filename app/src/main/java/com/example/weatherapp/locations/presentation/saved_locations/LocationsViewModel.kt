package com.example.weatherapp.locations.presentation.saved_locations

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
class LocationsViewModel(

): ViewModel() {

    private val _locationsState = MutableStateFlow(LocationsState())
    val locationsState = _locationsState.asStateFlow()

    fun onLocationsScreenEvent(locationsScreenEvent: LocationsScreenEvent) {
        when (locationsScreenEvent) {
            else -> Unit
        }
    }
}