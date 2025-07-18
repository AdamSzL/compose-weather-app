package com.example.weatherapp.weather.presentation.components.tiles.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.HourlyForecast
import com.example.weatherapp.core.presentation.components.WeatherIcon
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.fake.fakeDetailedWeather
import com.example.weatherapp.weather.presentation.utils.formatToHourMinute

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HourlyForecastTileItem(
    hourlyForecast: HourlyForecast,
    timezone: String,
    isNow: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = modifier.fillMaxHeight()
    ) {
        Box(
            modifier = if (isNow) {
                Modifier.clip(MaterialShapes.SoftBurst.toShape()).background(MaterialTheme.colorScheme.primary)
            } else {
                Modifier
            }
        ) {
            Text(
                text = stringResource(R.string.temperature_int, hourlyForecast.temperature),
                style = MaterialTheme.typography.bodyLarge,
                color = if (isNow) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            )
        }
        WeatherIcon(
            icon = hourlyForecast.icon,
            size = "2",
            modifier = Modifier.size(dimensionResource(R.dimen.size_small))
        )
        Text(
            text = if (isNow) stringResource(R.string.now) else formatToHourMinute(hourlyForecast.dt, timezone),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

class IsNowParameterProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun HourlyForecastTileItemPreview(
    @PreviewParameter(IsNowParameterProvider::class) isNow: Boolean
) {
    WeatherAppTheme {
        HourlyForecastTileItem(
            hourlyForecast = fakeDetailedWeather.hourlyForecast.first(),
            timezone = "Europe/Warsaw",
            isNow = isNow
        )
    }
}