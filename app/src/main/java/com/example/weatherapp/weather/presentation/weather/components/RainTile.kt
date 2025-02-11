package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

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
private fun PrecipitationTilePreview() {
    WeatherAppTheme {
        RainTile(
            rain = 2.73,
        )
    }
}

