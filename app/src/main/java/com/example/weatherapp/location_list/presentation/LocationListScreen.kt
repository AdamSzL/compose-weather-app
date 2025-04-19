package com.example.weatherapp.location_list.presentation

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
import com.example.weatherapp.location_list.presentation.components.AddLocationFloatingActionButton
import com.example.weatherapp.location_list.presentation.components.WeatherBriefList
import com.example.weatherapp.location_list.presentation.fake.fakeLocations
import com.example.weatherapp.location_search.presentation.user_location.RequestLocationPermission
import com.example.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    locationListState: LocationListState,
    selectedMapLocation: GeoPoint?,
    onLocationScreenEvent: (LocationListScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var locationPermissionAlreadyRequested by remember { mutableStateOf(false) }
    var isPermissionDialogVisible by remember { mutableStateOf(false) }
    var isFabExpanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(locationListState.message) {
        locationListState.message?.let { text ->
            snackbarHostState.showSnackbar(text.asString(context))
            onLocationScreenEvent(LocationListScreenEvent.ResetMessage)
        }
    }

    LaunchedEffect(selectedMapLocation) {
        selectedMapLocation?.let {
            onLocationScreenEvent(LocationListScreenEvent.AddMapLocation(it))
            onLocationScreenEvent(LocationListScreenEvent.ResetSavedMapLocation)
        }
    }

    if (isPermissionDialogVisible) {
        RequestLocationPermission(
            permissionAlreadyRequested = locationPermissionAlreadyRequested,
            onSettingsDialogDismiss = {
                onLocationScreenEvent(LocationListScreenEvent.ShowSnackbar(UiText.StringResource(R.string.location_access_disabled_enable_in_settings)))
                isPermissionDialogVisible = false
            },
            onGoToAppSettings = {
                onLocationScreenEvent(LocationListScreenEvent.GoToAppSettings)
            },
            onPermissionGranted = {
                locationPermissionAlreadyRequested = true
                isPermissionDialogVisible = false
                onLocationScreenEvent(LocationListScreenEvent.FetchUserLocation)
            },
            onPermissionDenied = {
                onLocationScreenEvent(LocationListScreenEvent.ShowSnackbar(UiText.StringResource(R.string.location_permission_required_for_this_feature)))
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
                    onLocationScreenEvent(LocationListScreenEvent.NavigateToLocationMap)
                },
                onNavigateToLocationSearch = {
                    onLocationScreenEvent(LocationListScreenEvent.NavigateToLocationSearch)
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
                locations = locationListState.locations,
                onNavigateToWeatherScreen = {
                    onLocationScreenEvent(LocationListScreenEvent.NavigateToWeatherScreen(it))
                },
                onLocationDelete = {
                    onLocationScreenEvent(LocationListScreenEvent.DeleteLocation(it))
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
            locationListState = LocationListState(
                locations = fakeLocations
            ),
            selectedMapLocation = null,
            onLocationScreenEvent = {}
        )
    }
}

