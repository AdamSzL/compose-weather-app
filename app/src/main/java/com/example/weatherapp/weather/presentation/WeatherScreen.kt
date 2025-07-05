package com.example.weatherapp.weather.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
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
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.formattedAddress
import com.example.weatherapp.core.fake.fakeLocations
import com.example.weatherapp.core.fake.fakeUserLocation
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.EditModeHorizontalToolbar
import com.example.weatherapp.weather.presentation.components.WeatherOverview
import com.example.weatherapp.weather.presentation.fake.fakeWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.fake.fakeWeatherTileData
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun WeatherRoot(
    locationId: Long,
    onNavigateBack: () -> Unit,
    weatherViewModel: WeatherViewModel = koinViewModel<WeatherViewModel>(
        parameters = { parametersOf(locationId) }
    ),
) {
    val weatherState by weatherViewModel.weatherState.collectAsStateWithLifecycle()

    WeatherScreen(
        location = fakeUserLocation,
        weatherState = weatherState,
        onWeatherScreenEvent = {
            when (it) {
                is WeatherScreenEvent.NavigateBack -> onNavigateBack()
                else -> Unit
            }
            weatherViewModel.onWeatherScreenEvent(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
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
            if (weatherState.weatherHeaderInfo != null) {
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
            EditModeHorizontalToolbar(
                weatherState = weatherState,
                onEnterEditMode = {
                    onWeatherScreenEvent(WeatherScreenEvent.ToggleEditMode(true))
                },
                onExitEditMode = {
                    onWeatherScreenEvent(WeatherScreenEvent.SaveLayoutAndExitEditMode)
                },
                onUndoLayoutChange = {
                    onWeatherScreenEvent(WeatherScreenEvent.UndoLayoutChange)
                },
                onRedoLayoutChange = {
                    onWeatherScreenEvent(WeatherScreenEvent.RedoLayoutChange)
                },
                onToggleTilesLock = {
                    onWeatherScreenEvent(WeatherScreenEvent.ToggleTilesLock(it))
                },
                onToggleDeleteMode = {
                    onWeatherScreenEvent(WeatherScreenEvent.ToggleDeleteMode(it))
                },
                onShuffleTiles = {
                    onWeatherScreenEvent(WeatherScreenEvent.ShuffleTiles)
                },
                modifier = Modifier
                    .align(BottomEnd)
                    .offset(x = -ScreenOffset, y = -ScreenOffset),
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
            location = fakeLocations.first(),
            weatherState = WeatherState(
                weatherHeaderInfo = fakeWeatherHeaderInfo,
                weatherTileData = fakeWeatherTileData,
                isEditModeEnabled = isEditModeEnabled
            ),
            onWeatherScreenEvent = {}
        )
    }
}