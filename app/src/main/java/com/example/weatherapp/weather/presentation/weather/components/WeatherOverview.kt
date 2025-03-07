package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.domain.WeatherHeaderInfo
import com.example.weatherapp.weather.domain.WeatherTileData
import com.example.weatherapp.weather.presentation.components.WeatherHeader
import com.example.weatherapp.weather.presentation.weather.fake.fakeWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.weather.fake.fakeWeatherTileData
import com.example.weatherapp.weather.presentation.weather.utils.shake
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyGridState

@Composable
fun WeatherOverview(
    weatherHeaderInfo: WeatherHeaderInfo,
    weatherTileData: List<WeatherTileData>,
    areTilesReorderable: Boolean,
    areTilesRemovable: Boolean,
    onDeleteTile: (String) -> Unit,
    onMoveTile: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyGridState = rememberLazyGridState()
    val reorderableLazyGridState = rememberReorderableLazyGridState(lazyGridState) { from, to ->
        onMoveTile(from.index - 1, to.index - 1)
    }
    val hapticFeedback = LocalHapticFeedback.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_big)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_big)),
        modifier = modifier
            .testTag("TilesGrid")
) {
        item(span = {
            GridItemSpan(maxLineSpan)
        }) {
            WeatherHeader(
                weatherHeaderInfo = weatherHeaderInfo,
                modifier = Modifier.testTag("WeatherHeader")
            )
        }
        items(weatherTileData, key = { it.tileId }) { tileData ->
            val tileModifier = Modifier.aspectRatio(1f).animateItem()
            if (areTilesReorderable) {
                ReorderableItem(
                    reorderableLazyGridState,
                    tileData.tileId
                ) {
                    WeatherTile(
                        tileData = tileData,
                        modifier = tileModifier
                            .draggableHandle()
                    )
                }
            } else {
                WeatherTile(
                    tileData = tileData,
                    modifier = tileModifier
                        .then(
                            if (areTilesRemovable) {
                                Modifier
                                    .shake()
                                    .clickable {
                                        onDeleteTile(tileData.tileId)
                                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                            } else {
                                Modifier
                            }
                        )
                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .testTag("GridBottomSpacer")
                    .height(dimensionResource(R.dimen.padding_big))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherDetailsPreview() {
    WeatherAppTheme {
        WeatherOverview(
            weatherHeaderInfo = fakeWeatherHeaderInfo,
            weatherTileData = fakeWeatherTileData,
            areTilesReorderable = true,
            areTilesRemovable = false,
            onDeleteTile = {},
            onMoveTile = { _, _ -> }
        )
    }
}