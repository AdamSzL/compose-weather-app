package com.example.weatherapp.weather.presentation.components.tiles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.WeatherValueUnitTile

@Composable
fun RainTile(
    rain: Double,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_rain,
        tileHeaderText = R.string.rain,
        value = "%.2f".format(rain),
        unit = R.string.mmh,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun RainTilePreview() {
    WeatherAppTheme {
        RainTile(
            rain = 2.73,
        )
    }
}

