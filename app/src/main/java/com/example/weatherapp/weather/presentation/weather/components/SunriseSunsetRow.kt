package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.WeatherCard
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.domain.SunriseSunsetDetails
import com.example.weatherapp.weather.domain.toSunriseSunsetDetails
import com.example.weatherapp.weather.presentation.weather.mock.mockWeatherInfo
import com.example.weatherapp.weather.presentation.weather.utils.convertTimestampToHourMinute

@Composable
fun SunriseSunsetRow(
    sunriseSunsetDetails: SunriseSunsetDetails,
    modifier: Modifier = Modifier
) {
    WeatherCard(
        headerIcon = R.drawable.ic_twilight_wb,
        headerText = R.string.sunrise_sunset,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_sun),
                    contentDescription = stringResource(R.string.sunrise),
                    modifier = Modifier.size(dimensionResource(R.dimen.size_small))
                )
                Text(
                    text = convertTimestampToHourMinute(sunriseSunsetDetails.sunrise),
                    style = MaterialTheme.typography.displaySmall,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_night),
                    contentDescription = stringResource(R.string.sunset),
                    modifier = Modifier.size(dimensionResource(R.dimen.size_small))
                )
                Text(
                    text = convertTimestampToHourMinute(sunriseSunsetDetails.sunset),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SunriseSunsetRowPreview() {
    WeatherAppTheme {
        SunriseSunsetRow(
            sunriseSunsetDetails = mockWeatherInfo.toSunriseSunsetDetails()
        )
    }
}

