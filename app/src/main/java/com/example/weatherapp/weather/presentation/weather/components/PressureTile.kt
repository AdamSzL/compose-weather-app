package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.WeatherCard
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun PressureTile(
    pressure: Int,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_pressure,
        tileHeaderText = R.string.pressure,
        value = pressure.toString(),
        unit = R.string.hpa,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun PressureTilePreview() {
    WeatherAppTheme {
        PressureTile(
            pressure = 1021
        )
    }
}

