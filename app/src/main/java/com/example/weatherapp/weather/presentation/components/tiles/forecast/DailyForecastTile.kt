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
import com.example.weatherapp.core.domain.model.DailyForecast
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.WeatherCard
import com.example.weatherapp.weather.presentation.fake.fakeDetailedWeather

@Composable
fun DailyForecastTile(
    dailyForecast: List<DailyForecast>,
    timezone: String,
    modifier: Modifier = Modifier
) {
    WeatherCard(
        headerIcon = R.drawable.ic_calendar,
        headerText = R.string.daily_forecast,
        modifier = modifier
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(
                items = dailyForecast,
                key = { _, item -> item.dt }
            ) { index, dailyForecastItem ->
                DailyForecastTileItem(
                    dailyForecast = dailyForecastItem,
                    timezone = timezone,
                    isToday = index == 0,
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
        DailyForecastTile(
            dailyForecast = fakeDetailedWeather.dailyForecast,
            timezone = "Europe/Warsaw"
        )
    }
}

