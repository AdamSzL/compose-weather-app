package com.example.weatherapp.weather.presentation.weather.components.tiles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.weather.components.WeatherValueUnitTile
import com.example.weatherapp.weather.presentation.weather.fake.fakeDetailedWeather

@Composable
fun VisibilityTile(
    visibility: Double,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_visibility,
        tileHeaderText = R.string.visibility,
        value = "%.1f".format(visibility),
        unit = R.string.km,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun VisibilityTilePreview() {
    WeatherAppTheme {
        VisibilityTile(
            visibility = fakeDetailedWeather.visibility
        )
    }
}

