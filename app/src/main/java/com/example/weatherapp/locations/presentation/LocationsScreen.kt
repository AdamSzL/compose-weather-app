package com.example.weatherapp.locations.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun LocationsScreen(
    locationsState: LocationsState,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Locations Screen"
    )
}

@Preview(showBackground = true)
@Composable
private fun LocationsScreenPreview() {
    WeatherAppTheme {
        LocationsScreen(
            locationsState = LocationsState()
        )
    }
}

