package com.example.weatherapp.weather.presentation.components.tiles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.WeatherValueUnitTile

@Composable
fun PressureTile(
    pressure: Int,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_pressure,
        tileHeaderText = R.string.pressure,
        value = pressure.toString(),
        unit = R.string.hpa,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun PressureTilePreview() {
    WeatherAppTheme {
        PressureTile(
            pressure = 1021
        )
    }
}

