package com.example.weatherapp.weather.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.request.ImageRequest
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.utils.weatherIconUrl
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.domain.WeatherHeaderInfo
import com.example.weatherapp.weather.domain.WeatherInfo
import com.example.weatherapp.weather.domain.toWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.weather.mock.mockWeatherInfo
import com.example.weatherapp.weather.presentation.weather.utils.capitalizeWords

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WeatherHeader(
    weatherInfo: WeatherHeaderInfo,
    modifier: Modifier = Modifier
) {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(Color.Red.toArgb())
    }

    with (weatherInfo) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = weatherDescription.capitalizeWords(),
                style = MaterialTheme.typography.headlineSmall,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(R.string.temperature, temperature),
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                    ) {
                        Text(
                            text = stringResource(R.string.temperature, maxTemperature)
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_thermostat_up),
                            contentDescription = stringResource(R.string.max_temperature)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
                    ) {
                        Text(
                            text = stringResource(R.string.temperature, minTemperature)
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_thermostat_down),
                            contentDescription = stringResource(R.string.min_temperature)
                        )
                    }
                }
                CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
                    AsyncImage(
                        model = weatherIconUrl.format(weatherIcon),
                        contentDescription = weatherDescription,
                        modifier = Modifier.size(dimensionResource(R.dimen.size_big)),
                    )
                }
            }
            Text(
                text = stringResource(R.string.feels_like, feelsLike)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherHeaderPreview() {
    WeatherAppTheme {
        WeatherHeader(
            weatherInfo = mockWeatherInfo.toWeatherHeaderInfo(),
        )
    }
}