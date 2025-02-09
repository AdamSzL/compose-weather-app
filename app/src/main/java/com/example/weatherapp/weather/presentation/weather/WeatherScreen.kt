package com.example.weatherapp.weather.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil3.compose.rememberAsyncImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.domain.toSunriseSunsetDetails
import com.example.weatherapp.weather.domain.toWeatherDetails
import com.example.weatherapp.weather.domain.toWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.components.WeatherHeader
import com.example.weatherapp.weather.presentation.weather.components.SunriseSunsetRow
import com.example.weatherapp.weather.presentation.weather.components.WeatherDetails
import com.example.weatherapp.weather.presentation.weather.mock.mockWeatherInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    weatherState: WeatherState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            if (weatherState.weatherInfo != null) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "${weatherState.weatherInfo.cityName}, ${weatherState.weatherInfo.country}",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    },
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        if (weatherState.weatherInfo != null) {
            with (weatherState.weatherInfo) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_big)),
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = dimensionResource(R.dimen.padding_big))
                        .padding(bottom = dimensionResource(R.dimen.padding_big))
                        .verticalScroll(scrollState)
                ) {
                    WeatherHeader(
                        weatherInfo = this@with.toWeatherHeaderInfo(),
                    )
                    WeatherDetails(
                        weatherDetails = this@with.toWeatherDetails(),
                    )
                    SunriseSunsetRow(
                        sunriseSunsetDetails = this@with.toSunriseSunsetDetails(),
                    )
                }
            }
        } else {
            Text(
                text = "loading data..."
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherScreenPreview() {
    WeatherAppTheme {
        WeatherScreen(
            weatherState = WeatherState(
                weatherInfo = mockWeatherInfo
            )
        )
    }
}

