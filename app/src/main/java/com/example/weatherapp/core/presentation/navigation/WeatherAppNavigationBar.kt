package com.example.weatherapp.core.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

