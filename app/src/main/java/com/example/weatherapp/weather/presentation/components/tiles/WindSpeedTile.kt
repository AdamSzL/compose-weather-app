package com.example.weatherapp.weather.presentation.components.tiles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.WeatherValueUnitTile

@Composable
fun WindSpeedTile(
    windSpeed: Double,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_wind,
        tileHeaderText = R.string.wind_speed,
        value = "%.2f".format(windSpeed),
        unit = R.string.ms,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun WindTilePreview() {
    WeatherAppTheme {
        WindSpeedTile(
            windSpeed = 4.09,
        )
    }
}

