package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.WeatherCard
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.domain.WeatherDetailsInfo
import com.example.weatherapp.weather.domain.toWeatherDetails
import com.example.weatherapp.weather.presentation.weather.mock.mockWeatherInfo

@Composable
fun WeatherDetails(
    weatherDetails: WeatherDetailsInfo,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_big)),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            WeatherCard(
                headerIcon = R.drawable.ic_wind,
                headerText = R.string.wind,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            ) {

            }
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_big)))
            WeatherCard(
                headerIcon = R.drawable.ic_precipitation,
                headerText = R.string.precipitation,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            ) {

            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            WeatherCard(
                headerIcon = R.drawable.ic_pressure,
                headerText = R.string.pressure,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            ) {

            }
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_big)))
            WeatherCard(
                headerIcon = R.drawable.ic_humidity,
                headerText = R.string.humidity,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            ) {

            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            WeatherCard(
                headerIcon = R.drawable.ic_cloud,
                headerText = R.string.cloudiness,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            ) {

            }
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_big)))
            WeatherCard(
                headerIcon = R.drawable.ic_visibility,
                headerText = R.string.visibility,
                modifier = Modifier.weight(1f).aspectRatio(1f)
            ) {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherDetailsPreview() {
    WeatherAppTheme {
        WeatherDetails(
            weatherDetails = mockWeatherInfo.toWeatherDetails()
        )
    }
}

