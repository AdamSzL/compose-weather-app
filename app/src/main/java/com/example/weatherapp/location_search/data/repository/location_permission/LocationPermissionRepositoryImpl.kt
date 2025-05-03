package com.example.weatherapp.location_search.data.repository.location_permission

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationPermissionRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): LocationPermissionRepository {

    override suspend fun wasLocationPermissionAlreadyDenied(): Flow<Boolean> {
        return dataStore.data
            .map { preferences -> preferences[WAS_LOCATION_PERMISSION_DENIED] ?: false }
    }

    override suspend fun markLocationPermissionDenied() {
        dataStore.edit { preferences ->
            preferences[WAS_LOCATION_PERMISSION_DENIED] = true
        }
    }

    companion object {
        val WAS_LOCATION_PERMISSION_DENIED = booleanPreferencesKey("was_location_permission_denied")
    }
}