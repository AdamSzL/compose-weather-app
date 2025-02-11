package com.example.weatherapp.weather.presentation.weather.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
fun WeatherValueUnitTile(
    @DrawableRes tileHeaderIcon: Int,
    @StringRes tileHeaderText: Int,
    value: String,
    @StringRes unit: Int,
    modifier: Modifier = Modifier
) {
    WeatherCard(
        headerIcon = tileHeaderIcon,
        headerText = tileHeaderText,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = stringResource(unit),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherValueUnitTilePreview() {
    WeatherAppTheme {
        WeatherValueUnitTile(
            tileHeaderIcon = R.drawable.ic_pressure,
            tileHeaderText = R.string.pressure,
            value = "1021",
            unit = R.string.hpa,
        )
    }
}

