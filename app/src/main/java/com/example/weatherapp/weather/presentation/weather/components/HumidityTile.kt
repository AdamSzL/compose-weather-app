package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.WeatherCard
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun HumidityTile(
    humidity: Int,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_humidity,
        tileHeaderText = R.string.humidity,
        value = humidity.toString(),
        unit = R.string.percent,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun HumidityTilePreview() {
    WeatherAppTheme {
        HumidityTile(
            humidity = 60
        )
    }
}

