package com.example.weatherapp.weather.presentation.components.tiles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.WeatherValueUnitTile

@Composable
fun CloudinessTile(
    cloudiness: Int,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_cloud,
        tileHeaderText = R.string.cloudiness,
        value = cloudiness.toString(),
        unit = R.string.percent,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun CloudinessTilePreview() {
    WeatherAppTheme {
        CloudinessTile(
            cloudiness = 85
        )
    }
}

