package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.WeatherCard
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun VisibilityTile(
    visibility: Int,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_visibility,
        tileHeaderText = R.string.visibility,
        value = visibility.toString(),
        unit = R.string.km,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun VisibilityTilePreview() {
    WeatherAppTheme {
        VisibilityTile(
            visibility = 10
        )
    }
}

