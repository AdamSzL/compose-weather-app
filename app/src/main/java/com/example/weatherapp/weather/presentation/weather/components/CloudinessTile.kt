package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.WeatherCard
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun CloudinessTile(
    cloudiness: Int,
    modifier: Modifier = Modifier
) {
    WeatherValueUnitTile(
        tileHeaderIcon = R.drawable.ic_cloud,
        tileHeaderText = R.string.cloudiness,
        value = cloudiness.toString(),
        unit = R.string.percent,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun CloudinessTilePreview() {
    WeatherAppTheme {
        CloudinessTile(
            cloudiness = 85
        )
    }
}

