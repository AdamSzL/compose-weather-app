package com.example.weatherapp.weather.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.WeatherIcon
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.fake.fakeWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.model.WeatherHeaderInfo
import com.example.weatherapp.weather.presentation.utils.capitalizeWords

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WeatherHeader(
    weatherHeaderInfo: WeatherHeaderInfo,
    modifier: Modifier = Modifier
) {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(Color.Red.toArgb())
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = weatherHeaderInfo.description.capitalizeWords(),
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
                    text = stringResource(R.string.temperature, weatherHeaderInfo.temperature),
                    style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            WeatherIcon(
                icon = weatherHeaderInfo.icon,
                size = "4",
                modifier = Modifier.size(dimensionResource(R.dimen.size_big)),
            )
        }
        Text(
            text = stringResource(R.string.feels_like, weatherHeaderInfo.feelsLike)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherHeaderPreview() {
    WeatherAppTheme {
        WeatherHeader(
            weatherHeaderInfo = fakeWeatherHeaderInfo,
        )
    }
}