package com.example.weatherapp.forecast.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.ui.theme.WeatherAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForecastRoot(
    forecastViewModel: ForecastViewModel = koinViewModel<ForecastViewModel>(),
) {
    val forecastState by forecastViewModel.forecastState.collectAsStateWithLifecycle()

    ForecastScreen(
        forecastState = forecastState,
        onForecastScreenEvent = forecastViewModel::onForecastScreenEvent
    )
}

@Composable
fun ForecastScreen(
    forecastState: ForecastState,
    onForecastScreenEvent: (ForecastScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Forecast screen"
    )
}

@Preview(showBackground = true)
@Composable
private fun ForecastScreenPreview() {
    WeatherAppTheme {
        ForecastScreen(
            forecastState = ForecastState(),
            onForecastScreenEvent = {}
        )
    }
}

