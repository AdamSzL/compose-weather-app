package com.example.weatherapp.locations.presentation.saved_locations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.locations.presentation.components.AddLocationFloatingActionButton
import com.example.weatherapp.locations.presentation.saved_locations.components.WeatherBriefList
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeLocations
import com.example.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    locationsState: LocationsState,
    selectedMapLocation: GeoPoint?,
    onLocationScreenEvent: (LocationsScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var locationPermissionAlreadyRequested by remember { mutableStateOf(false) }
    var isPermissionDialogVisible by remember { mutableStateOf(false) }
    var isFabExpanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(locationsState.message) {
        locationsState.message?.let { text ->
            snackbarHostState.showSnackbar(text.asString(context))
            onLocationScreenEvent(LocationsScreenEvent.ResetMessage)
        }
    }

    LaunchedEffect(selectedMapLocation) {
        selectedMapLocation?.let {
            onLocationScreenEvent(LocationsScreenEvent.AddMapLocation(it))
            onLocationScreenEvent(LocationsScreenEvent.ResetSavedMapLocation)
        }
    }

    if (isPermissionDialogVisible) {
        RequestLocationPermission(
            permissionAlreadyRequested = locationPermissionAlreadyRequested,
            onSettingsDialogDismiss = {
                onLocationScreenEvent(LocationsScreenEvent.ShowSnackbar(UiText.StringResource(R.string.location_access_disabled_enable_in_settings)))
                isPermissionDialogVisible = false
            },
            onGoToAppSettings = {
                onLocationScreenEvent(LocationsScreenEvent.GoToAppSettings)
            },
            onPermissionGranted = {
                locationPermissionAlreadyRequested = true
                isPermissionDialogVisible = false
                onLocationScreenEvent(LocationsScreenEvent.FetchUserLocation)
            },
            onPermissionDenied = {
                onLocationScreenEvent(LocationsScreenEvent.ShowSnackbar(UiText.StringResource(R.string.location_permission_required_for_this_feature)))
                locationPermissionAlreadyRequested = true
                isPermissionDialogVisible = false
            },
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.locations)
                    )
                }
            )
        },
        floatingActionButton = {
            AddLocationFloatingActionButton(
                isFabExpanded = isFabExpanded,
                onExpandedToggle = {
                    isFabExpanded = !isFabExpanded
                },
                onFetchUserLocation = {
                    isPermissionDialogVisible = true
                    isFabExpanded = false
                },
                onNavigateToLocationMap = {
                    onLocationScreenEvent(LocationsScreenEvent.NavigateToLocationMap)
                },
                onNavigateToLocationSearch = {
                    onLocationScreenEvent(LocationsScreenEvent.NavigateToLocationSearch)
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            WeatherBriefList(
                locations = locationsState.locations,
                onNavigateToWeatherScreen = {
                    onLocationScreenEvent(LocationsScreenEvent.NavigateToWeatherScreen(it))
                },
                onLocationDelete = {
                    onLocationScreenEvent(LocationsScreenEvent.DeleteLocation(it))
                },
                modifier = Modifier.fillMaxSize()
            )

            if (isFabExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable { isFabExpanded = false }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationsScreenPreview() {
    WeatherAppTheme {
        LocationsScreen(
            locationsState = LocationsState(
                locations = fakeLocations
            ),
            selectedMapLocation = null,
            onLocationScreenEvent = {}
        )
    }
}

