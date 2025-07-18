package com.example.weatherapp.weather.presentation.components.tiles.forecast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.DailyForecast
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.WeatherCard
import com.example.weatherapp.weather.presentation.fake.fakeDetailedWeather

@Composable
fun DailyForecastTile(
    dailyForecast: List<DailyForecast>,
    modifier: Modifier = Modifier
) {
    val unit = R.string.ms
    WeatherCard(
        headerIcon = R.drawable.ic_calendar,
        headerText = R.string.daily_forecast,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hello",
                    style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.tertiary
                )
                unit?.let {
                    Text(
                        text = stringResource(unit),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DailyForecastTilePreview() {
    WeatherAppTheme {
        DailyForecastTile(
            dailyForecast = fakeDetailedWeather.dailyForecast
        )
    }
}

