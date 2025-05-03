package com.example.weatherapp.location_search.data.repository.location_permission

import kotlinx.coroutines.flow.Flow

interface LocationPermissionRepository {

    suspend fun wasLocationPermissionAlreadyDenied(): Flow<Boolean>

    suspend fun markLocationPermissionDenied()
}