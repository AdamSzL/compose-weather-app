package com.example.weatherapp.weather.presentation.weather

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherScreen(
    weatherState: WeatherState,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Current Weather"
    )
}

@Preview(showBackground = true)
@Composable
private fun WeatherScreenPreview() {
    WeatherAppTheme {
        WeatherScreen(
            weatherState = WeatherState()
        )
    }
}

