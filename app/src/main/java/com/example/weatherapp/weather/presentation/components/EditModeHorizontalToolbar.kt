package com.example.weatherapp.weather.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.vibrantFloatingToolbarColors
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.WeatherState
import com.example.weatherapp.weather.presentation.fake.fakeWeatherTileData

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EditModeHorizontalToolbar(
    weatherState: WeatherState,
    onEnterEditMode: () -> Unit,
    onExitEditMode: () -> Unit,
    onUndoLayoutChange: () -> Unit,
    onRedoLayoutChange: () -> Unit,
    onToggleTilesLock: (Boolean) -> Unit,
    onToggleDeleteMode: (Boolean) -> Unit,
    onShuffleTiles: () -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalFloatingToolbar(
        expanded = weatherState.isEditModeEnabled,
        floatingActionButton = {
            FloatingToolbarDefaults.VibrantFloatingActionButton(
                onClick = {
                    if (weatherState.isEditModeEnabled) {
                        onExitEditMode()
                    } else {
                        onEnterEditMode()
                    }
                }
            ) {
                Icon(
                    imageVector = if (weatherState.isEditModeEnabled) Icons.Filled.Check else Icons.Filled.Edit,
                    stringResource(R.string.edit_mode)
                )
            }
        },
        colors = vibrantFloatingToolbarColors(),
        content = {
            IconButton(
                enabled = weatherState.currentWeatherTileDataIndex > 0,
                onClick = onUndoLayoutChange,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_undo),
                    contentDescription = stringResource(R.string.undo_layout_change)
                )
            }
            IconButton(
                enabled = weatherState.currentWeatherTileDataIndex < weatherState.weatherTileDataHistory.size - 1,
                onClick = onRedoLayoutChange,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_redo),
                    contentDescription = stringResource(R.string.redo_layout_change)
                )
            }
            FilledTonalIconToggleButton(
                checked = weatherState.areTilesLocked,
                onCheckedChange = { onToggleTilesLock(it) },
            ) {
                AnimatedContent(
                    targetState = weatherState.areTilesLocked,
                ) { areTilesLocked ->
                    if (areTilesLocked) {
                        Icon(
                            painter = painterResource(R.drawable.ic_lock_closed),
                            contentDescription = stringResource(R.string.unlock_tiles)
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_lock_open),
                            contentDescription = stringResource(R.string.lock_tiles)
                        )
                    }
                }
            }
            FilledIconToggleButton(
                checked = weatherState.isDeleteModeEnabled,
                onCheckedChange = { onToggleDeleteMode(it) },
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.delete_tiles)
                )
            }
            IconButton(
                onClick = onShuffleTiles,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_shuffle),
                    contentDescription = stringResource(R.string.shuffle_tiles)
                )
            }
        },
        modifier = modifier,
    )
}

class WeatherStateParameterProvider: PreviewParameterProvider<WeatherState> {
    override val values: Sequence<WeatherState>
        get() = sequenceOf(
            WeatherState(isEditModeEnabled = false),
            WeatherState(isEditModeEnabled = true, isDeleteModeEnabled = false),
            WeatherState(isEditModeEnabled = true, isDeleteModeEnabled = true),
            WeatherState(isEditModeEnabled = true, areTilesLocked = true),
            WeatherState(isEditModeEnabled = true, isSavingLayout = true),
            WeatherState(isEditModeEnabled = true, weatherTileDataHistory = List(3) { fakeWeatherTileData }, currentWeatherTileDataIndex = 0),
            WeatherState(isEditModeEnabled = true, weatherTileDataHistory = List(3) { fakeWeatherTileData }, currentWeatherTileDataIndex = 1),
            WeatherState(isEditModeEnabled = true, weatherTileDataHistory = List(3) { fakeWeatherTileData }, currentWeatherTileDataIndex = 2)
        )
}
        
@Preview(showBackground = true)
@Composable
private fun EditModeHorizontalToolbarPreview(
    @PreviewParameter(WeatherStateParameterProvider ::class) weatherState: WeatherState
) {
    WeatherAppTheme {
        EditModeHorizontalToolbar(
            weatherState = weatherState,
            onEnterEditMode = {},
            onExitEditMode = {},
            onUndoLayoutChange = {},
            onRedoLayoutChange = {},
            onToggleTilesLock = {},
            onToggleDeleteMode = {},
            onShuffleTiles = {}
        )
    }
}