package com.example.weatherapp.weather.presentation.components.tiles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.WeatherValueUnitTile
import com.example.weatherapp.weather.presentation.fake.fakeDetailedWeather
import com.example.weatherapp.weather.presentation.mapper.uviToRiskLevel

@Composable
fun UviTile(
    uvi: Double,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_sun,
        tileHeaderText = R.string.uv_index,
        value = uvi.toString(),
        unit = uviToRiskLevel(uvi),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun UviTilePreview() {
    WeatherAppTheme {
        UviTile(
            uvi = fakeDetailedWeather.uvi
        )
    }
}
