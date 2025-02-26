package com.example.weatherapp.locations.presentation.saved_locations

import android.Manifest
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.ConfirmAlertDialog
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    permissionAlreadyRequested: Boolean,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onSettingsDialogDismiss: () -> Unit,
    onGoToAppSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    ) { result ->
        if (!result) {
            onPermissionDenied()
        }
    }
    val launchPermissionRequest = locationPermissionState::launchPermissionRequest

    when {
        locationPermissionState.status.isGranted -> {
            onPermissionGranted()
        }
        !permissionAlreadyRequested && !locationPermissionState.status.shouldShowRationale -> {
            SideEffect {
                launchPermissionRequest()
            }
        }
        locationPermissionState.status.shouldShowRationale -> {
            LocationPermissionRationaleDialog(
                onDialogDismiss = onPermissionDenied,
                onPermissionDialogLaunch = {
                    launchPermissionRequest()
                }
            )
        }
        else -> {
            ConfirmAlertDialog(
                title = stringResource(R.string.location_permission_required),
                text = stringResource(R.string.location_access_required_feature),
                icon = Icons.Default.LocationOn,
                onDismissRequest = onSettingsDialogDismiss,
                onConfirmation = onGoToAppSettings
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestLocationPermissionPreview() {
    WeatherAppTheme {
        RequestLocationPermission(
            permissionAlreadyRequested = true,
            onSettingsDialogDismiss = {},
            onGoToAppSettings = {},
            onPermissionGranted = {},
            onPermissionDenied = {},
        )
    }
}

