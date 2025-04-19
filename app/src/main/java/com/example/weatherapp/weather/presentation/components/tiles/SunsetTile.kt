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
fun SunsetTile(
    sunset: Long,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_night,
        tileHeaderText = R.string.sunset,
        value = convertTimestampToHourMinute(sunset),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SunsetTilePreview() {
    WeatherAppTheme {
        SunsetTile(
            sunset = fakeDetailedWeather.sunset,
        )
    }
}

