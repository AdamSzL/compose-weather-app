package com.example.weatherapp.weather.presentation.components.tiles.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.annotation.ExperimentalCoilApi
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.HourlyForecast
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.WeatherCard
import com.example.weatherapp.weather.presentation.fake.fakeDetailedWeather

@Composable
fun HourlyForecastTile(
    hourlyForecast: List<HourlyForecast>,
    timezone: String,
    modifier: Modifier = Modifier
) {
    WeatherCard(
        headerIcon = R.drawable.ic_clock,
        headerText = R.string.hourly_forecast,
        modifier = modifier
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_big)),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(
                items = hourlyForecast,
                key = { _, item -> item.dt }
            ) { index, hourlyForecastItem ->
                HourlyForecastTileItem(
                    hourlyForecast = hourlyForecastItem,
                    timezone = timezone,
                    isNow = index == 0
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true)
@Composable
private fun HourlyForecastTilePreview() {
    WeatherAppTheme {
        HourlyForecastTile(
            hourlyForecast = fakeDetailedWeather.hourlyForecast,
            timezone = "Europe/Warsaw"
        )
    }
}

