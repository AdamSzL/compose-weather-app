package com.example.weatherapp.location_search.presentation.user_location

import android.Manifest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.ConfirmAlertDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionHandler(
    wasPermissionAlreadyDenied: Boolean,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onOpenAppSettings: () -> Unit,
) {
    var shouldShowRationale by remember { mutableStateOf(true) }
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    ) { result ->
        if (!result) {
            onPermissionDenied()
        }
    }

    when {
        locationPermissionState.status.isGranted -> {
            onPermissionGranted()
        }
        locationPermissionState.status.shouldShowRationale -> {
            if (shouldShowRationale) {
                LocationPermissionRationaleDialog(
                    onDialogDismiss = {
                        onPermissionDenied()
                    },
                    onPermissionDialogLaunch = {
                        shouldShowRationale = false
                        locationPermissionState.launchPermissionRequest()
                    }
                )
            }
        }
        wasPermissionAlreadyDenied -> {
            ConfirmAlertDialog(
                title = stringResource(R.string.location_permission_required),
                text = stringResource(R.string.location_access_required_feature),
                icon = Icons.Default.LocationOn,
                onDismissRequest = onPermissionDenied,
                onConfirmation = onOpenAppSettings,
            )
        }
        else -> {
            LaunchedEffect(Unit) {
                locationPermissionState.launchPermissionRequest()
            }
        }
    }
}