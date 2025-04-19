package com.example.weatherapp.weather.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.formattedAddress
import com.example.weatherapp.location_list.presentation.fake.fakeLocations
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.EditModeFloatingActionButton
import com.example.weatherapp.weather.presentation.components.EditModeTopAppBar
import com.example.weatherapp.weather.presentation.components.WeatherOverview
import com.example.weatherapp.weather.presentation.fake.fakeWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.fake.fakeWeatherTileData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    location: GeoLocation,
    weatherState: WeatherState,
    onWeatherScreenEvent: (WeatherScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val fullLocationName by remember(location.address) {
        mutableStateOf(location.address.formattedAddress())
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(weatherState.message) {
        weatherState.message?.let { text ->
            snackbarHostState.showSnackbar(text.asString(context))
            onWeatherScreenEvent(WeatherScreenEvent.ResetMessage)
        }
    }

    Scaffold(
        topBar = {
            if (weatherState.weatherHeaderInfo != null && !weatherState.isEditModeEnabled) {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onWeatherScreenEvent(WeatherScreenEvent.NavigateBack)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.go_back_to_locations)
                            )
                        }
                    },
                    title = {
                        Text(
                            text = fullLocationName,
                            style = MaterialTheme.typography.headlineMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                )
            } else if (weatherState.isEditModeEnabled) {
                EditModeTopAppBar(
                    weatherState = weatherState,
                    onWeatherScreenEvent = onWeatherScreenEvent
                )
            }
        },
        floatingActionButton = {
            AnimatedContent(
                targetState = weatherState.isEditModeEnabled,
            ) { isEditModeEnabled ->
                if (isEditModeEnabled) {
                    EditModeFloatingActionButton(
                        isDeleting = weatherState.isDeleteModeEnabled,
                        onToggleDeleteMode = {
                            onWeatherScreenEvent(WeatherScreenEvent.ToggleDeleteMode(it))
                        },
                        onSaveLayoutAndExitEditingMode = {
                            onWeatherScreenEvent(WeatherScreenEvent.SaveLayoutAndExitEditMode)
                        }
                    )
                } else {
                    ExtendedFloatingActionButton(
                        onClick = {
                            onWeatherScreenEvent(WeatherScreenEvent.ToggleEditMode(true))
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(R.string.edit)
                            )
                        }
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            WeatherOverview(
                weatherHeaderInfo = weatherState.weatherHeaderInfo!!,
                weatherTileData = weatherState.weatherTileData,
                areTilesReorderable = weatherState.isEditModeEnabled && !weatherState.isDeleteModeEnabled && !weatherState.isSavingLayout && !weatherState.areTilesLocked,
                areTilesRemovable = weatherState.isDeleteModeEnabled,
                onDeleteTile = {
                    onWeatherScreenEvent(WeatherScreenEvent.DeleteTile(it))
                },
                onMoveTile = { from, to ->
                    onWeatherScreenEvent(WeatherScreenEvent.MoveTile(from, to))
                },
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_big))
            )
        }
    }
}

class IsEditModeEnabledParameterProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun WeatherScreenPreview(
    @PreviewParameter(IsEditModeEnabledParameterProvider::class) isEditModeEnabled: Boolean
) {
    WeatherAppTheme {
        WeatherScreen(
            location = fakeLocations.first().location,
            weatherState = WeatherState(
                weatherHeaderInfo = fakeWeatherHeaderInfo,
                weatherTileData = fakeWeatherTileData,
                isEditModeEnabled = isEditModeEnabled
            ),
            onWeatherScreenEvent = {}
        )
    }
}