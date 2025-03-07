package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.domain.WeatherTileData
import kotlin.math.roundToInt

@Composable
fun DraggableWeatherTile(
    tileData: WeatherTileData,
    modifier: Modifier = Modifier
) {
    var animateStart by remember { mutableStateOf(false) }

    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        animateStart = true
    }

    WeatherTile(
        tileData = tileData,
        modifier = modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = {

                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    },
                    onDragEnd = {

                    }
                )
            }
    )
}

@Preview(showBackground = true)
@Composable
private fun DraggableWeatherTilePreview() {
    WeatherAppTheme {
        DraggableWeatherTile(
            tileData = WeatherTileData.Rain(2.23),
        )
    }
}

