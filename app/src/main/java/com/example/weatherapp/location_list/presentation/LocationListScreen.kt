package com.example.weatherapp.location_list.presentation

import android.app.Activity
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.consume
import com.example.weatherapp.core.fake.fakeLocationWeatherBriefs
import com.example.weatherapp.core.presentation.components.ScreenWithLoadingContent
import com.example.weatherapp.location_list.presentation.components.AddLocationFloatingActionButtonMenu
import com.example.weatherapp.location_list.presentation.components.WeatherBriefList
import com.example.weatherapp.location_search.presentation.user_location.LocationPermissionHandler
import com.example.weatherapp.openAppSettings
import com.example.weatherapp.ui.theme.WeatherAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LocationListRoot(
    onNavigateToMapScreen: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToWeatherScreen: (Long) -> Unit,
    locationListViewModel: LocationListViewModel = koinViewModel<LocationListViewModel>(),
) {
    val context = LocalContext.current
    val locationListState by locationListViewModel.locationsState.collectAsStateWithLifecycle()

    LocationListScreen(
        locationListState = locationListState,
        onLocationScreenEvent = {
            when (it) {
                LocationListScreenEvent.NavigateToLocationMap -> onNavigateToMapScreen()
                LocationListScreenEvent.NavigateToLocationSearch -> onNavigateToSearchScreen()
                is LocationListScreenEvent.NavigateToWeatherScreen -> onNavigateToWeatherScreen(it.location.id)
                LocationListScreenEvent.GoToAppSettings -> (context as Activity).openAppSettings()
                else -> Unit
            }
            locationListViewModel.onLocationsScreenEvent(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListScreen(
    locationListState: LocationListState,
    onLocationScreenEvent: (LocationListScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var isPermissionDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isFabExpanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    locationListState.showMessageEvent?.let {
        LaunchedEffect(it) {
            snackbarHostState.showSnackbar(it.data.asString(context))
            it.consume()
        }
    }

    val hidePermissionDialog = {
        isPermissionDialogVisible = false
    }

    if (isPermissionDialogVisible) {
        LocationPermissionHandler(
            wasPermissionAlreadyDenied = locationListState.wasLocationPermissionAlreadyDenied,
            onPermissionGranted = {
                onLocationScreenEvent(LocationListScreenEvent.FetchUserLocation)
                hidePermissionDialog()
            },
            onPermissionDenied = {
                onLocationScreenEvent(LocationListScreenEvent.LocationPermissionWasDenied)
                hidePermissionDialog()
            },
            onOpenAppSettings = {
                onLocationScreenEvent(LocationListScreenEvent.GoToAppSettings)
                hidePermissionDialog()
            }
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
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        ScreenWithLoadingContent(
            isLoadingContent = locationListState.isLoadingLocations,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                WeatherBriefList(
                    locations = locationListState.locationsWithWeatherBrief,
                    isRefreshingWeatherBriefs = locationListState.isRefreshingWeatherBriefs,
                    onNavigateToWeatherScreen = {
                        onLocationScreenEvent(LocationListScreenEvent.NavigateToWeatherScreen(it))
                    },
                    onLocationDelete = {
                        onLocationScreenEvent(LocationListScreenEvent.DeleteLocation(it))
                    },
                    onWeatherBriefRefresh = {
                        onLocationScreenEvent(LocationListScreenEvent.RefreshSavedLocationsWeatherBrief)
                    },
                    modifier = Modifier.fillMaxSize()
                )

                AddLocationFloatingActionButtonMenu(
                    isMenuExpanded = isFabExpanded,
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
                    modifier = Modifier.align(BottomEnd)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationsScreenPreview() {
    WeatherAppTheme {
        LocationListScreen(
            locationListState = LocationListState(
                locationsWithWeatherBrief = fakeLocationWeatherBriefs
            ),
            onLocationScreenEvent = {}
        )
    }
}

