package com.example.weatherapp.core.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.weatherapp.core.presentation.navigation.WeatherAppScreen
import com.example.weatherapp.location_list.presentation.LocationListRoot
import com.example.weatherapp.location_search.presentation.map.LocationMapRoot
import com.example.weatherapp.location_search.presentation.place_search.LocationSearchRoot
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.WeatherRoot

@Composable
fun WeatherApplication(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WeatherAppScreen.LocationListScreen,
        popExitTransition = {
            scaleOut(
                targetScale = 0.9f,
                transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0.5f)
            )
        },
        popEnterTransition = { EnterTransition.None },
        modifier = modifier
    ) {
        composable<WeatherAppScreen.WeatherScreen> { backStackEntry ->
            val locationId = backStackEntry.toRoute<WeatherAppScreen.WeatherScreen>().locationId
            WeatherRoot(
                locationId = locationId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<WeatherAppScreen.LocationListScreen> {
            LocationListRoot(
                onNavigateToMapScreen = { navController.navigate(WeatherAppScreen.LocationMapScreen) },
                onNavigateToSearchScreen = { navController.navigate(WeatherAppScreen.LocationSearchScreen) },
                onNavigateToWeatherScreen = { navController.navigate(WeatherAppScreen.WeatherScreen(it)) },
            )
        }
        composable<WeatherAppScreen.LocationMapScreen> {
            LocationMapRoot(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<WeatherAppScreen.LocationSearchScreen> {
            LocationSearchRoot(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherApplicationPreview() {
    WeatherAppTheme {
        WeatherApplication()
    }
}

