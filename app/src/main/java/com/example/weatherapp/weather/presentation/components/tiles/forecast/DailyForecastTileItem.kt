package com.example.weatherapp.weather.presentation.components.tiles.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.DailyForecast
import com.example.weatherapp.core.presentation.components.WeatherIcon
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.fake.fakeDetailedWeather
import com.example.weatherapp.weather.presentation.utils.formatToMonthDay

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DailyForecastTileItem(
    dailyForecast: DailyForecast,
    timezone: String,
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_large)))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_small),
                vertical = dimensionResource(R.dimen.padding_big),
            )
        ) {
            Text(
                text = stringResource(R.string.temperature_int, dailyForecast.tempMax),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = stringResource(R.string.temperature_int, dailyForecast.tempMin),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            WeatherIcon(
                icon = dailyForecast.icon,
                size = "2",
                modifier = Modifier.size(dimensionResource(R.dimen.size_normal))
            )
            Text(
                text = if (isToday) stringResource(R.string.today) else formatToMonthDay(dailyForecast.dt, timezone),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

class IsTodayParameterProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}
        
@Preview(showBackground = true)
@Composable
private fun DailyForecastTileItemPreview(
    @PreviewParameter(IsTodayParameterProvider::class) isToday: Boolean
) {
    WeatherAppTheme {
        DailyForecastTileItem(
            dailyForecast = fakeDetailedWeather.dailyForecast.first(),
            timezone = "Europe/Warsaw",
            isToday = isToday
        )
    }
}