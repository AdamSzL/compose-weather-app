package com.example.weatherapp.location_search.presentation.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.consume
import com.example.weatherapp.location_search.presentation.map.utils.toGeoPoint
import com.example.weatherapp.location_search.presentation.map.utils.toLatLng
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import org.koin.androidx.compose.koinViewModel

@Composable
fun LocationMapRoot(
    onNavigateBack: () -> Unit,
    locationMapViewModel: LocationMapViewModel = koinViewModel<LocationMapViewModel>(),
) {
    val locationMapState by locationMapViewModel.locationMapState.collectAsStateWithLifecycle()
    LocationMapScreen(
        locationMapState = locationMapState,
        onLocationMapScreenEvent = { event ->
            when (event) {
                is LocationMapScreenEvent.NavigateBack -> onNavigateBack()
                else -> Unit
            }
            locationMapViewModel.onLocationMapScreenEvent(event)
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LocationMapScreen(
    locationMapState: LocationMapState,
    onLocationMapScreenEvent: (LocationMapScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val markerState = remember { MarkerState() }
    val navigateBack = { onLocationMapScreenEvent(LocationMapScreenEvent.NavigateBack) }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    locationMapState.navigateBackEvent?.let {
        LaunchedEffect(it) {
            navigateBack()
            it.consume()
        }
    }

    locationMapState.showMessageEvent?.let {
        LaunchedEffect(it) {
            snackbarHostState.showSnackbar(it.data.asString(context))
            it.consume()
        }
    }

    LaunchedEffect(locationMapState.selectedLocation) {
        locationMapState.selectedLocation?.let {
            markerState.position = it.toLatLng()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .fillMaxSize()
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                onMapClick = { latLng ->
                    onLocationMapScreenEvent(LocationMapScreenEvent.SelectLocation(latLng.toGeoPoint()))
                }
            ) {
                locationMapState.selectedLocation?.let {
                    Marker(
                        state = markerState,
                        title = stringResource(R.string.selected_location)
                    )
                }
            }
            FilledTonalIconButton(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = dimensionResource(R.dimen.padding_big)),
                onClick = navigateBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_back)
                )
            }
            FilledIconButton(
                onClick = {
                    onLocationMapScreenEvent(LocationMapScreenEvent.SaveSelectedLocation)
                },
                modifier =
                    Modifier
                        .align(BottomCenter)
                        .padding(bottom = dimensionResource(R.dimen.padding_large))
                        .size(
                            IconButtonDefaults.largeContainerSize(
                                IconButtonDefaults.IconButtonWidthOption.Uniform
                            )
                        ),
                shape = IconButtonDefaults.largeRoundShape,
                enabled = locationMapState.selectedLocation != null && !locationMapState.isSavingLocation,
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(IconButtonDefaults.largeIconSize),
                )
            }
//            LocationMapControls(
//                isCancelEnabled = !locationMapState.isSavingLocation,
//                isSaveEnabled = locationMapState.selectedLocation != null && !locationMapState.isSavingLocation,
//                onCancel = navigateBack,
//                onSave = {
//                    onLocationMapScreenEvent(LocationMapScreenEvent.SaveSelectedLocation)
//                },
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = dimensionResource(R.dimen.size_medium))
//                    .clip(RoundedCornerShape(dimensionResource(R.dimen.size_small)))
//                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
//                    .padding(
//                        horizontal = dimensionResource(R.dimen.padding_medium),
//                        vertical = dimensionResource(R.dimen.padding_small)
//                    )
//            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    WeatherAppTheme {
        LocationMapScreen(
            locationMapState = LocationMapState(),
            onLocationMapScreenEvent = {}
        )
    }
}