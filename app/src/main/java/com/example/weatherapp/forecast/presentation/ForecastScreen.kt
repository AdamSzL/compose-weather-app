package com.example.weatherapp.forecast.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun ForecastScreen(
    forecastState: ForecastState,
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
            forecastState = ForecastState()
        )
    }
}

