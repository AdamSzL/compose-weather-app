package com.example.weatherapp.weather.presentation.components.tiles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.WeatherValueUnitTile
import com.example.weatherapp.weather.presentation.fake.fakeDetailedWeather
import com.example.weatherapp.weather.presentation.utils.convertTimestampToHourMinute

@Composable
fun SunriseTile(
    sunrise: Long,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_sun,
        tileHeaderText = R.string.sunrise,
        value = convertTimestampToHourMinute(sunrise),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SunriseTilePreview() {
    WeatherAppTheme {
        SunriseTile(
            sunrise = fakeDetailedWeather.sunrise,
        )
    }
}

