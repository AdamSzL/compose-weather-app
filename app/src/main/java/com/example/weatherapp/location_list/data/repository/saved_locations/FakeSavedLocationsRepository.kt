package com.example.weatherapp.location_list.data.repository.saved_locations

import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.fake.fakeLocations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeSavedLocationsRepository(
    private val shouldReturnError: Boolean = false
): SavedLocationsRepository {

    private val fakeLocationList = fakeLocations.toMutableList()
    private val fakeLocationsFlow = MutableStateFlow(fakeLocationList.toList())

    override suspend fun getAllLocations(): Flow<List<GeoLocation>> {
        return fakeLocationsFlow
    }

    override suspend fun getLocationById(locationId: Long): GeoLocation {
        return fakeLocationList.first { it.id == locationId }
    }

    override suspend fun insertLocation(location: GeoLocation) {
        fakeLocationList.removeAll { it.id == location.id }
        fakeLocationList.add(location)
        fakeLocationsFlow.update { fakeLocations.toList() }
    }

    override suspend fun deleteLocation(locationId: Long) {
        fakeLocationList.removeAll { it.id == locationId }
        fakeLocationsFlow.update { fakeLocations.toList() }
    }

}