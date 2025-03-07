package com.example.weatherapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherAppNavigationBar(
    currentScreen: WeatherAppScreen,
    onNavigationItemClicked: (WeatherAppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
}

@Preview(showBackground = true)
@Composable
private fun WeatherAppNavigationBarPreview() {
    WeatherAppTheme {
        WeatherAppNavigationBar(
            currentScreen = WeatherAppScreen.WeatherScreen,
            onNavigationItemClicked = {}
        )
    }
}

