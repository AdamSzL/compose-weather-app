package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.weather.WeatherScreenEvent
import com.example.weatherapp.weather.presentation.weather.WeatherState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditModeTopAppBar(
    weatherState: WeatherState,
    onWeatherScreenEvent: (WeatherScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditModeMenuExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(
                text = stringResource(if (weatherState.isDeleteModeEnabled) R.string.delete_mode else R.string.edit_mode),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onWeatherScreenEvent(WeatherScreenEvent.ToggleEditMode(false))
                },
                enabled = !weatherState.isSavingLayout
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.exit_edit_mode)
                )
            }
        },
        actions = {
            Text(
                text = stringResource(if (weatherState.isSavingLayout) R.string.saving else R.string.saved)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_big)))
            if (weatherState.isSavingLayout) {
                CircularProgressIndicator(
                    modifier = Modifier.size(dimensionResource(R.dimen.size_tiny))
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {
                    onWeatherScreenEvent(WeatherScreenEvent.ToggleTilesLock(!weatherState.areTilesLocked))
                }
            ) {
                Icon(
                    painter = painterResource(if (weatherState.areTilesLocked) R.drawable.ic_lock_closed else R.drawable.ic_lock_open),
                    contentDescription = stringResource(if (weatherState.areTilesLocked) R.string.unlock_tiles else R.string.lock_tiles)
                )
            }
            EditModeDropdownMenu(
                expanded = isEditModeMenuExpanded,
                isDeleteModeEnabled = weatherState.isDeleteModeEnabled,
                isAutoSaveEnabled = weatherState.isAutoSaveEnabled,
                isSavingLayout = weatherState.isSavingLayout,
                isUndoEnabled = weatherState.currentWeatherTileDataIndex > 0,
                isRedoEnabled = weatherState.currentWeatherTileDataIndex < weatherState.weatherTileDataHistory.size - 1,
                onExpandedToggle = { isEditModeMenuExpanded = it },
                onResetTiles = {
                    onWeatherScreenEvent(WeatherScreenEvent.ResetLayout)
                },
                onShuffleTiles = {
                    onWeatherScreenEvent(WeatherScreenEvent.ShuffleTiles)
                },
                onToggleAutoSave = {
                    onWeatherScreenEvent(WeatherScreenEvent.ToggleAutoSave(it))
                },
                onSaveLayout = {
                    onWeatherScreenEvent(WeatherScreenEvent.SaveLayout)
                },
                onUndoLayoutChange = {
                    onWeatherScreenEvent(WeatherScreenEvent.UndoLayoutChange)
                },
                onRedoLayoutChange = {
                    onWeatherScreenEvent(WeatherScreenEvent.RedoLayoutChange)
                },
                onToggleDeleteMode = {
                    onWeatherScreenEvent(WeatherScreenEvent.ToggleDeleteMode(it))
                },
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun EditModeTopAppBarPreview() {
    WeatherAppTheme {
        EditModeTopAppBar(
            weatherState = WeatherState(),
            onWeatherScreenEvent = {}
        )
    }
}

