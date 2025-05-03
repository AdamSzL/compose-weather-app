package com.example.weatherapp.location_search.data.repository.location_permission

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocationPermissionRepository: LocationPermissionRepository {

    private var wasLocationPermissionAlreadyDenied = false

    override suspend fun wasLocationPermissionAlreadyDenied(): Flow<Boolean> = flow {
        emit(wasLocationPermissionAlreadyDenied)
    }

    override suspend fun markLocationPermissionDenied() {
        wasLocationPermissionAlreadyDenied = true
    }
}