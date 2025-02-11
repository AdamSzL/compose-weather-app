package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.domain.WeatherInfo
import com.example.weatherapp.weather.presentation.weather.mock.mockWeatherInfo

@Composable
fun WeatherTiles(
    weatherInfo: WeatherInfo,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_big)),
        modifier = modifier
            .fillMaxWidth()
    ) {
        WindTile(
            windSpeed = weatherInfo.windSpeed,
            windDirection = weatherInfo.windDirection,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            RainTile(
                rain = weatherInfo.rain,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_big)))
            SnowTile(
                snow = weatherInfo.snow,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            PressureTile(
                pressure = weatherInfo.pressure,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_big)))
            HumidityTile(
                humidity = weatherInfo.humidity,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            CloudinessTile(
                cloudiness = weatherInfo.cloudiness,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_big)))
            VisibilityTile(
                visibility = 10,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            )
        }
        SunriseSunsetTile(
            sunrise = weatherInfo.sunrise,
            sunset = weatherInfo.sunset,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherDetailsPreview() {
    WeatherAppTheme {
        WeatherTiles(
            weatherInfo = mockWeatherInfo
        )
    }
}

