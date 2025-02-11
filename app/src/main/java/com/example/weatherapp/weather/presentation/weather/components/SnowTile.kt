package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun SnowTile(
    snow: Double,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_snow,
        tileHeaderText = R.string.snow,
        value = "%.2f".format(snow),
        unit = R.string.mmh,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SnowTilePreview() {
    WeatherAppTheme {
        SnowTile(
            snow = 2.67,
        )
    }
}

